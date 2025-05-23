package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.QuestionTypeRepository;
import com.survey.geneza.domain.QuestionType;
import com.survey.geneza.service.QuestionTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("QuestionTypeService")
@Transactional
public class QuestionTypeServiceImpl implements QuestionTypeService {

    @Autowired
    private QuestionTypeRepository questionTypeRepository;
    public QuestionTypeServiceImpl() {
    }

    @Transactional
    public QuestionType findById(Integer id) {
        return questionTypeRepository.findById(id);
    }

    @Transactional
    public List<QuestionType> findAll() {
        return questionTypeRepository.findAll();
    }
     
    @Transactional
    public void saveQuestionType(QuestionType questionType) {
        QuestionType existingQuestionType = questionTypeRepository.findById(questionType.getId());
        if (existingQuestionType != null) {
        if (existingQuestionType != questionType) {      
        existingQuestionType.setId(questionType.getId());
                existingQuestionType.setQuestionType(questionType.getQuestionType());
        }
        questionType = questionTypeRepository.save(existingQuestionType);
    }else{
        questionType = questionTypeRepository.save(questionType);
        }
        questionTypeRepository.flush();
    }

    public boolean deleteQuestionType(Integer questionTypeId) {
        QuestionType questionType = questionTypeRepository.findById(questionTypeId);
        if(questionType!=null) {
            questionTypeRepository.delete(questionType);
            return true;
        }else {
            return false;
        }
    }

    

}