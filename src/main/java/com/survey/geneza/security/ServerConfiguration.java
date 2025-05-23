package  com.survey.geneza.security;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;




@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerConfiguration {

	private static final String RESOURCE_ID = "restservice";
	

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {
		
		@Value("${spring.datasource.driverClassName}")
	    private String oauthClass;

	    @Value("${spring.datasource.url}")
	    private String oauthUrl;
	    
	    @Value("${spring.datasource.username}")
	    private String dbUser;
	    
	    @Value("${spring.datasource.password}")
	    private String dbPW;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources
				.resourceId(RESOURCE_ID)
				.tokenStore(tokenStore());
		}

		@Override

		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().//
					antMatchers(HttpMethod.GET, "/Project/**").hasAnyRole("USER").//					
					antMatchers(HttpMethod.PUT, "/Project/**").hasAnyRole("USER").//
					antMatchers(HttpMethod.POST, "/Project/**").hasAnyRole("USER").and().//
					csrf().disable();
					http.csrf().disable();
					http.headers().frameOptions().disable();
		}
		
		@Bean
	    public TokenStore tokenStore() {
	        DataSource tokenDataSource = DataSourceBuilder.create()
	                        .driverClassName(oauthClass)
	                        .username(dbUser)
	                        .password(dbPW)
	                        .url(oauthUrl)
	                        .build();
	        return new JdbcTokenStore(tokenDataSource);
	    }
			
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {
		@Autowired
		private TokenStore tokenStore;

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Autowired
		private CustomUserDetailsService userDetailsService;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
				.tokenStore(this.tokenStore)
				.authenticationManager(this.authenticationManager)
				.userDetailsService(userDetailsService);
        }
		

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
					.withClient("clientapp")
						.authorizedGrantTypes("password", "refresh_token")
						.authorities("USER")
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)
						.secret(passwordEncoder().encode("123456"));
		}

		@Autowired
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}


		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(this.tokenStore);
			return tokenServices;
		}
		
		
		
		
	}
}