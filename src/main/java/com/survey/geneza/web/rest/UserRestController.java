package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.User;
import com.survey.geneza.domain.Person;
import com.survey.geneza.persistence.UserRepository;
import com.survey.geneza.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import io.swagger.v3.oas.annotations.Hidden;


@CrossOrigin
@Controller("UserRestController")
@Hidden
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/DunamisUser", method = RequestMethod.PUT)
    @ResponseBody
    public User saveUser(@RequestBody User user) {
    	userService.saveUser(user);
        return userService.findById(user.getId());
    }

    @RequestMapping(value = "/DunamisUser", method = RequestMethod.POST)
    @ResponseBody
    public User newUser(@RequestBody User user) {
    	user = userService.saveUser(user);
        return userRepository.findById(user.getId());
    }
    
    @RequestMapping(value = "/DunamisUser/GetPerson" , method = RequestMethod.GET)
    @ResponseBody
    public Person getPerson() {
    	return userService.getPerson();
    }

    
    /*
    @RequestMapping(value = "/User/Login", method = RequestMethod.POST)
    @ResponseBody
    public SameepUser  loadUserByUserName(@RequestBody SameepUser userDetails){
    	SameepUser user = new SameepUser();
    	try{  
  	      user = fetch(userDetails.getUserName(),userDetails.getPassword());  
  	      }catch(Exception m){
  	    	  System.out.println(m);
  	    	  if (m.getMessage() =="Invalid User"){
  	    		  user.setId(-999);
  	    	  }
  	    	  
  	    	 if (m.getMessage() =="Invalid Password"){
 	    		  user.setId(-9999);

 	    	  }
  	    	 
  	    	 if (m.getMessage() =="Not Enabled"){
	    		  user.setId(-99999);
	    		
	    	  }
  	      }  
  	      return user;  
    }
    
    private SameepUser fetch(String userName, String password)throws InvalidLoginException{  
    	SameepUser user = userRepository.findByUserName(userName);
    	System.out.println(userName);
    	if(user != null){  
    		System.out.println(password);
    		System.out.println(user.getPassword());
    		System.out.println(user.getPassword().equals(password));
    		if (user.getPassword().equals(password)==true)
    			if(user.getEnabled()==true)
    				return user;
    			else
    				throw new InvalidLoginException("Not Enabled");	
    		else
    			throw new InvalidLoginException("Invalid Password");	
    	}else        	
        	throw new InvalidLoginException("Invalid User");
         
      }  
    */
    }