package  com.survey.geneza.persistence;  
import  com.survey.geneza.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {	 
    User findById(Integer id);
    User findByLogin(String login);
    User findByPersonId(Integer personId);
    List<User> findByPersonFirstName(String firstName);
    List<User> findAll();
    //public List<User> findAllByPersonId(Integer personId);
    //public User findByUserName(String userName);
}