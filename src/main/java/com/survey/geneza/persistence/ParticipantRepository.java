package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {	 
    Participant findById(Integer id);
    List<Participant> findAll();
    public List<Participant> findAllByPersonId(Integer personId);  
   Page<Participant> findAll(Pageable pageable);

}