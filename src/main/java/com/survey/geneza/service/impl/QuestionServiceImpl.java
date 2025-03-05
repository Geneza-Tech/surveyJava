package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.QuestionRepository;
import com.survey.geneza.domain.Question;
import com.survey.geneza.service.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("QuestionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    public QuestionServiceImpl() {
    }

    @Transactional
    public Question findById(Integer id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }
     
    @Transactional
    public void saveQuestion(Question question) {
        Question existingQuestion = questionRepository.findById(question.getId());
        if (existingQuestion != null) {
        if (existingQuestion != question) {      
        existingQuestion.setId(question.getId());
                existingQuestion.setSurvey(question.getSurvey());
                existingQuestion.setSection(question.getSection());
                existingQuestion.setQuestionType(question.getQuestionType());
                existingQuestion.setChildOrder(question.getChildOrder());
                existingQuestion.setSurveyOrder(question.getSurveyOrder());
                existingQuestion.setParentQuestion(question.getParentQuestion());
                existingQuestion.setText(question.getText());
        }
        question = questionRepository.save(existingQuestion);
    }else{
        question = questionRepository.save(question);
        }
        questionRepository.flush();
    }

    public boolean deleteQuestion(Integer questionId) {
        Question question = questionRepository.findById(questionId);
        if(question!=null) {
            questionRepository.delete(question);
            return true;
        }else {
            return false;
        }
    }@Transactional
    public List<Question> findAllBySurveyId(Integer  surveyId) {
        return new java.util.ArrayList<Question>(questionRepository.findAllBySurveyId(surveyId));
    }@Transactional
    public List<Question> findAllByQuestionTypeId(Integer  questionTypeId) {
        return new java.util.ArrayList<Question>(questionRepository.findAllByQuestionTypeId(questionTypeId));
    }@Transactional
    public List<Question> findAllByParentQuestionId(Integer  parentQuestionId) {
        return new java.util.ArrayList<Question>(questionRepository.findAllByParentQuestionId(parentQuestionId));
    }

    

}