package  com.survey.geneza.service;
import  com.survey.geneza.domain.Role;
import java.util.List;

public interface RoleService {
    public Role findById(Integer id);
    public void saveRole(Role role);
    public List<Role> findAll();
  //  public List<User> findAllByPersonId(Integer  person);
}