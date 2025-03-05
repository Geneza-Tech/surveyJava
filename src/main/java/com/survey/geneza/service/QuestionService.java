package com.survey.geneza.service;
import com.survey.geneza.domain.Question;
import java.util.List;

public interface QuestionService {
    public Question findById(Integer id);
    public void saveQuestion(Question question_1);
    public boolean deleteQuestion(Integer questionId);
    public List<Question> findAll();
    public List<Question> findAllBySurveyId(Integer  survey);
    public List<Question> findAllByQuestionTypeId(Integer  questionType);
    public List<Question> findAllByParentQuestionId(Integer  parentQuestion);
}