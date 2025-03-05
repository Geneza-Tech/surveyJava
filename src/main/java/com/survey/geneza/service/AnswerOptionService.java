package com.survey.geneza.service;
import com.survey.geneza.domain.AnswerOption;
import java.util.List;

public interface AnswerOptionService {
    public AnswerOption findById(Integer id);
    public void saveAnswerOption(AnswerOption answerOption_1);
    public boolean deleteAnswerOption(Integer answerOptionId);
    public List<AnswerOption> findAll();
    public List<AnswerOption> findAllByQuestionId(Integer  question);
}