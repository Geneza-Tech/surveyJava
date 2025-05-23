package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.AnswerOptionTemplateItemRepository;
import com.survey.geneza.domain.AnswerOptionTemplateItem;
import com.survey.geneza.service.AnswerOptionTemplateItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AnswerOptionTemplateItemService")
@Transactional
public class AnswerOptionTemplateItemServiceImpl implements AnswerOptionTemplateItemService {

    @Autowired
    private AnswerOptionTemplateItemRepository answerOptionTemplateItemRepository;
    public AnswerOptionTemplateItemServiceImpl() {
    }

    @Transactional
    public AnswerOptionTemplateItem findById(Integer id) {
        return answerOptionTemplateItemRepository.findById(id);
    }

    @Transactional
    public List<AnswerOptionTemplateItem> findAll() {
        return answerOptionTemplateItemRepository.findAll();
    }
     
    @Transactional
    public void saveAnswerOptionTemplateItem(AnswerOptionTemplateItem answerOptionTemplateItem) {
        AnswerOptionTemplateItem existingAnswerOptionTemplateItem = answerOptionTemplateItemRepository.findById(answerOptionTemplateItem.getId());
        if (existingAnswerOptionTemplateItem != null) {
        if (existingAnswerOptionTemplateItem != answerOptionTemplateItem) {      
        existingAnswerOptionTemplateItem.setId(answerOptionTemplateItem.getId());
                existingAnswerOptionTemplateItem.setAnswerOptionTemplate(answerOptionTemplateItem.getAnswerOptionTemplate());
                existingAnswerOptionTemplateItem.setAnswerOption(answerOptionTemplateItem.getAnswerOption());
        }
        answerOptionTemplateItem = answerOptionTemplateItemRepository.save(existingAnswerOptionTemplateItem);
    }else{
        answerOptionTemplateItem = answerOptionTemplateItemRepository.save(answerOptionTemplateItem);
        }
        answerOptionTemplateItemRepository.flush();
    }

    public boolean deleteAnswerOptionTemplateItem(Integer answerOptionTemplateItemId) {
        AnswerOptionTemplateItem answerOptionTemplateItem = answerOptionTemplateItemRepository.findById(answerOptionTemplateItemId);
        if(answerOptionTemplateItem!=null) {
            answerOptionTemplateItemRepository.delete(answerOptionTemplateItem);
            return true;
        }else {
            return false;
        }
    }@Transactional
    public List<AnswerOptionTemplateItem> findAllByAnswerOptionTemplateId(Integer  answerOptionTemplateId) {
        return new java.util.ArrayList<AnswerOptionTemplateItem>(answerOptionTemplateItemRepository.findAllByAnswerOptionTemplateId(answerOptionTemplateId));
    }

    

}