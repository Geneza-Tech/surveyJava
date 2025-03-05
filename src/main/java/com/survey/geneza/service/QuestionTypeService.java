package com.survey.geneza.service;
import com.survey.geneza.domain.QuestionType;
import java.util.List;

public interface QuestionTypeService {
    public QuestionType findById(Integer id);
    public void saveQuestionType(QuestionType questionType_1);
    public boolean deleteQuestionType(Integer questionTypeId);
    public List<QuestionType> findAll();
}