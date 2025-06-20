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
        return questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }
     
    @Transactional
    public void saveQuestion(Question question) {
        if (question.getParentQuestion() != null && question.getId() != null &&
        question.getId().equals(question.getParentQuestion().getId())) {
        throw new IllegalArgumentException("A question cannot be its own parent.");
    }


        Question existingQuestion = question.getId() != null
            ? questionRepository.findById(question.getId()).orElse(null)
            : null;

        if (existingQuestion != null) {
            existingQuestion.setSurvey(question.getSurvey());
            existingQuestion.setSection(question.getSection());
            existingQuestion.setQuestionType(question.getQuestionType());
            existingQuestion.setChildOrder(question.getChildOrder());
            existingQuestion.setSurveyOrder(question.getSurveyOrder());
            existingQuestion.setParentQuestion(question.getParentQuestion());
            existingQuestion.setText(question.getText());
            questionRepository.save(existingQuestion);
        } else {
            questionRepository.save(question);
        }

        questionRepository.flush();
    }

    public boolean deleteQuestion(Integer questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            questionRepository.delete(question);
            return true;
        }
        return false;
    }
    
    @Transactional
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