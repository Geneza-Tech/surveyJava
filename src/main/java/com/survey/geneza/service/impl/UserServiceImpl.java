package  com.survey.geneza.service.impl;
import  com.survey.geneza.persistence.UserRepository;
import  com.survey.geneza.persistence.RoleRepository;
import  com.survey.geneza.persistence.PersonRepository;
import  com.survey.geneza.domain.User;
import  com.survey.geneza.domain.Role;
import  com.survey.geneza.domain.Person;
import  com.survey.geneza.service.UserService;
import  com.survey.geneza.utilities.NotificationService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private NotificationService notificationService;
    
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    
    public UserServiceImpl() {
    }

    @Transactional
    public User findById(Integer id) {
        return userRepository.findById(id);
    }
    


    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }
     
    @Transactional
    public User saveUser(User user) {
        
        passwordEncoder = new BCryptPasswordEncoder();

        
        User existingUser = userRepository.findById(user.getId());
        if (existingUser != null) {
        if (existingUser != user) {    
            existingUser.setId(user.getId());
            existingUser.setLogin(user.getLogin());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setPerson(user.getPerson());
        }
        user = userRepository.save(existingUser);
    }else{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        
       
        Role role = roleRepository.findById(1);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        
        Person person = new Person();
        person.setEmail(user.getLogin());
        person = personRepository.save(person);
        
        user.setPerson(person);
        userRepository.save(user);
        

        
        
        try {
                
                System.out.println("I have arrived");
                notificationService.sendNotification("Anand Niketan Donor Management account acivated",
                        "Dear " + user.getPerson().getFirstName() + 
                        ",\nThanks for signing up. Click here to login and see sponsorship opportunities, perform transactions and receive updates." +
                        "\nhttp://localhost:8083/app/index.html#/login" +
                        "\n\nRegards," +
                        "\nTeam Dunamis"
                        ,user.getPerson().getEmail());
            }catch( Exception e ){
                logger.info("Error Sending Email: " + e.getMessage());
            }
           
        
        }
        userRepository.flush();
        personRepository.flush();
        return user;
    }

    
    public Person getPerson(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        return userRepository.findByLogin(auth.getName()).getPerson();
    }
    
 
}