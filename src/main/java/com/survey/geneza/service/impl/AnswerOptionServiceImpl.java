package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.AnswerOptionRepository;
import com.survey.geneza.domain.AnswerOption;
import com.survey.geneza.service.AnswerOptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AnswerOptionService")
@Transactional
public class AnswerOptionServiceImpl implements AnswerOptionService {

    @Autowired
    private AnswerOptionRepository answerOptionRepository;
    public AnswerOptionServiceImpl() {
    }

    @Transactional
    public AnswerOption findById(Integer id) {
        return answerOptionRepository.findById(id);
    }

    @Transactional
    public List<AnswerOption> findAll() {
        return answerOptionRepository.findAll();
    }
     
    @Transactional
    public void saveAnswerOption(AnswerOption answerOption) {
        AnswerOption existingAnswerOption = answerOptionRepository.findById(answerOption.getId());
        if (existingAnswerOption != null) {
        if (existingAnswerOption != answerOption) {      
        existingAnswerOption.setId(answerOption.getId());
                existingAnswerOption.setQuestion(answerOption.getQuestion());
                existingAnswerOption.setOptionValue(answerOption.getOptionValue());
                existingAnswerOption.setSequence(answerOption.getSequence());
        }
        answerOption = answerOptionRepository.save(existingAnswerOption);
    }else{
        answerOption = answerOptionRepository.save(answerOption);
        }
        answerOptionRepository.flush();
    }

    public boolean deleteAnswerOption(Integer answerOptionId) {
        AnswerOption answerOption = answerOptionRepository.findById(answerOptionId);
        if(answerOption!=null) {
            answerOptionRepository.delete(answerOption);
            return true;
        }else {
            return false;
        }
    }@Transactional
    public List<AnswerOption> findAllByQuestionId(Integer  questionId) {
        return new java.util.ArrayList<AnswerOption>(answerOptionRepository.findAllByQuestionId(questionId));
    }

    

}