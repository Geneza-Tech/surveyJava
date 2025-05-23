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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import io.swagger.v3.oas.annotations.Hidden;



@CrossOrigin
@Controller("UserRestController")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/surveyUser", method = RequestMethod.PUT)
    @ResponseBody
    public User saveUser(@RequestBody User user) {
    	userService.saveUser(user);
        return userService.findById(user.getId());
    }

    @RequestMapping(value = "/surveyUser", method = RequestMethod.POST)
    @ResponseBody
    public User newUser(@RequestBody User user) {
    	user = userService.saveUser(user);
        return userRepository.findById(user.getId());
    }
    
    @RequestMapping(value = "/surveyUser/GetPerson" , method = RequestMethod.GET)
    @ResponseBody
    public Person getPerson() {
    	return userService.getPerson();
    }

    
    
//    @PostMapping("/surveyUser/Login")
// @ResponseBody
// public User loadUserByUserName(@RequestBody User userDetails) {
//     User user = new User();
//     try {
//         user = fetch(userDetails.getLogin(), userDetails.getPassword());
//     } catch (InvalidLoginException m) {
//         System.out.println(m.getMessage());
//         if ("Invalid User".equals(m.getMessage())) {
//             user.setId(-999);
//         } else if ("Invalid Password".equals(m.getMessage())) {
//             user.setId(-9999);
//         } else if ("Not Enabled".equals(m.getMessage())) {
//             user.setId(-99999);
//         }
//     }
//     return user;
// }

// private User fetch(String userName, String password) throws InvalidLoginException {  
//     User user = userRepository.findByUserName(userName);
//     System.out.println(userName);
//     if (user != null) {  
//         System.out.println(password);
//         System.out.println(user.getPassword());
//         if (user.getPassword() != null && user.getPassword().equals(password)) {
//             if (user.getEnabled()) {
//                 return user;
//             } else {
//                 throw new InvalidLoginException("Not Enabled");
//             }
//         } else {
//             throw new InvalidLoginException("Invalid Password");
//         }
//     } else {        
//         throw new InvalidLoginException("Invalid User");
//     }
// }

    }