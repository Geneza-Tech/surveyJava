package  com.survey.geneza.utilities; 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import  com.survey.geneza.domain.Person;


@Service
public class PaymentService {	    
    //Prod
    //String apiKey = "27965f9c7c8264c133e1c7547ae771c4"
    //String authToken = "4d0b3ec405783d2c2911845a33403ba0"
	//String apiUrl = "https://www.instamojo.com/api/1.1/payment-requests/";
	
	//Test
	String apiKey = "af2d3b904b519e75a232230ec9ad9fe4";
    String authToken = "93a5473c713b23ba5b4aaf18397249da";	
    String apiUrl = "https://test.instamojo.com/api/1.1/payment-requests/";
    
    public String[] getPaymentRequestDetails(String paymentRequestId){
    	String[] paymentRequestDetails = new String[10];
    	HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(apiUrl + paymentRequestId);
		
		try {
			// ad header
			/*post.setHeader("X-Api-Key", "27965f9c7c8264c133e1c7547ae771c4");
			post.setHeader("X-Auth-Token", "4d0b3ec405783d2c2911845a33403ba0"); */
			
			get.setHeader("X-Api-Key", apiKey);
			get.setHeader("X-Auth-Token", authToken);
			HttpResponse response = client.execute(get);
			
			System.out.println("\nSending 'GET' request to URL : " + apiUrl);
			System.out.println("Response Code : " +
	                                    response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println("line " + line);
				if(line.contains("status")) paymentRequestDetails[0] = line;
				if(line.contains("\"payment_id\":")) paymentRequestDetails[1]= line;
				result.append(line);
			}
			
			System.out.println(result.toString());
			return paymentRequestDetails;
		}catch( Exception e ){
			System.out.println(e);
			return null;
		}
    }
    
    public String[] getPaymentDetails(String paymentId,String paymentRequestId){
    	String[] paymentDetails = new String[10];
    	return paymentDetails;
    }
    
    public String[] getPaymentURL(Double amount, String purpose, Person person) {
    	String[] paymentDetails = new String[10]; 
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(apiUrl);
		
		try {
			// ad header
			/*post.setHeader("X-Api-Key", "27965f9c7c8264c133e1c7547ae771c4");
			post.setHeader("X-Auth-Token", "4d0b3ec405783d2c2911845a33403ba0"); */
			
			post.setHeader("X-Api-Key", apiKey);
			post.setHeader("X-Auth-Token", authToken);
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("allow_repeated_payments", "False"));
			urlParameters.add(new BasicNameValuePair("amount", amount.toString()));
			urlParameters.add(new BasicNameValuePair("buyer_name", person.getFirstName() + " " + person.getLastName() ));
			urlParameters.add(new BasicNameValuePair("purpose", purpose));
			urlParameters.add(new BasicNameValuePair("redirect_url", "http://localhost:8090/app/index.html#/home/Route"));
			urlParameters.add(new BasicNameValuePair("phone", person.getPhone()));
			urlParameters.add(new BasicNameValuePair("send_email", "TRUE"));
			urlParameters.add(new BasicNameValuePair("webhook/send_sms", "TRUE"));
			urlParameters.add(new BasicNameValuePair("email", person.getEmail()));
		
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + apiUrl);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " +
	                                    response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println("line " + line);
				if(line.contains("longurl")) paymentDetails[0] = line;
				if(line.contains("\"id\":")) paymentDetails[1]= line;
				result.append(line);
			}
			
			System.out.println(result.toString());
			return paymentDetails;
		}catch( Exception e ){
			System.out.println(e);
			return null;
		}
    }

}