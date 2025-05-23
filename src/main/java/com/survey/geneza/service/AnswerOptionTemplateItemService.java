package com.survey.geneza.service;
import com.survey.geneza.domain.AnswerOptionTemplateItem;
import java.util.List;

public interface AnswerOptionTemplateItemService {
    public AnswerOptionTemplateItem findById(Integer id);
    public void saveAnswerOptionTemplateItem(AnswerOptionTemplateItem answerOptionTemplateItem_1);
    public boolean deleteAnswerOptionTemplateItem(Integer answerOptionTemplateItemId);
    public List<AnswerOptionTemplateItem> findAll();
    public List<AnswerOptionTemplateItem> findAllByAnswerOptionTemplateId(Integer  answerOptionTemplate);
}