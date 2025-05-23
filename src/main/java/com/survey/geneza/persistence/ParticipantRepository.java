package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {	 
    Participant findById(Integer id);
    List<Participant> findAll();
    public List<Participant> findAllByPersonId(Integer personId);  
   Page<Participant> findAll(Pageable pageable);
   @Query("SELECT p FROM Participant p JOIN p.person per JOIN User u ON per.id = u.person.id WHERE u.id = :userId")
    List<Participant> findAllByUserId(@Param("userId") Integer userId);


}