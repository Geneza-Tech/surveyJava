package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.AnswerOptionRepository;
import com.survey.geneza.persistence.AnswerOptionTemplateItemRepository;
import com.survey.geneza.persistence.AnswerOptionTemplateRepository;
import com.survey.geneza.persistence.QuestionRepository;
import com.survey.geneza.domain.AnswerOption;
import com.survey.geneza.domain.AnswerOptionTemplate;
import com.survey.geneza.domain.AnswerOptionTemplateItem;
import com.survey.geneza.service.AnswerOptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional; // ✔ make sure this is here
import org.springframework.data.jpa.repository.JpaRepository;
import com.survey.geneza.domain.Question;



@Service("AnswerOptionService")
@Transactional
public class AnswerOptionServiceImpl implements AnswerOptionService {

    @Autowired
    private AnswerOptionTemplateRepository answerOptionTemplateRepository;

    @Autowired
    private AnswerOptionTemplateItemRepository answerOptionTemplateItemRepository;

     @Autowired
    private QuestionRepository questionRepository;

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

    @Override
public void createAnswerOptionsFromTemplate(Integer answerOptionTemplateId, Integer questionId) {
    // Fetch AnswerOptionTemplate or throw exception
    AnswerOptionTemplate template = answerOptionTemplateRepository.findById(answerOptionTemplateId)
        .orElseThrow(() -> new RuntimeException("AnswerOptionTemplate not found with ID: " + answerOptionTemplateId));

    // Fetch Question or throw exception
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

    // Fetch all template items belonging to the template
    List<AnswerOptionTemplateItem> templateItems = 
        answerOptionTemplateItemRepository.findByAnswerOptionTemplate_Id(answerOptionTemplateId);

    int sequence = 1;
    for (AnswerOptionTemplateItem item : templateItems) {
        AnswerOption option = new AnswerOption();
        option.setOptionValue(item.getAnswerOption());
        option.setSequence(sequence++);
        option.setQuestion(question);
        answerOptionRepository.save(option);
    }
}

@Override
    public List<AnswerOption> getAnswerOptionsBySurveyId(Integer surveyId) {
        return answerOptionRepository.findAnswerOptionsBySurveyId(surveyId);
    }



}