package com.survey.geneza.service;
import com.survey.geneza.domain.AnswerOptionTemplate;
import java.util.List;

public interface AnswerOptionTemplateService {
    public AnswerOptionTemplate findById(Integer id);
    public void saveAnswerOptionTemplate(AnswerOptionTemplate answerOptionTemplate_1);
    public boolean deleteAnswerOptionTemplate(Integer answerOptionTemplateId);
    public List<AnswerOptionTemplate> findAll();
}