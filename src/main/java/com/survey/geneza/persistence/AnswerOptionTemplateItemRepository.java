package com.survey.geneza.persistence;  
import com.survey.geneza.domain.AnswerOptionTemplateItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface AnswerOptionTemplateItemRepository extends JpaRepository<AnswerOptionTemplateItem, Long> {	 
    AnswerOptionTemplateItem findById(Integer id);
    List<AnswerOptionTemplateItem> findAll();
    public List<AnswerOptionTemplateItem> findAllByAnswerOptionTemplateId(Integer answerOptionTemplateId);  
   Page<AnswerOptionTemplateItem> findAll(Pageable pageable);
    List<AnswerOptionTemplateItem> findByAnswerOptionTemplate_Id(Integer answerOptionTemplateId);
}