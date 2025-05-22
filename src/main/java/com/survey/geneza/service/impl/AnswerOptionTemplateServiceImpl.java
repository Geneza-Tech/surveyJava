package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.AnswerOptionTemplateRepository;
import com.survey.geneza.domain.AnswerOptionTemplate;
import com.survey.geneza.service.AnswerOptionTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AnswerOptionTemplateService")
@Transactional
public class AnswerOptionTemplateServiceImpl implements AnswerOptionTemplateService {

    @Autowired
    private AnswerOptionTemplateRepository answerOptionTemplateRepository;
    public AnswerOptionTemplateServiceImpl() {
    }

    @Transactional
    public AnswerOptionTemplate findById(Integer id) {
        return answerOptionTemplateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Template not found with ID: " + id));

    }

    @Transactional
    public List<AnswerOptionTemplate> findAll() {
        return answerOptionTemplateRepository.findAll();
    }
     
    @Transactional
    public void saveAnswerOptionTemplate(AnswerOptionTemplate answerOptionTemplate) {
        AnswerOptionTemplate existing = answerOptionTemplate.getId() != null
            ? answerOptionTemplateRepository.findById(answerOptionTemplate.getId()).orElse(null)
            : null;

        if (existing != null) {
            existing.setAnswerOptionTemplate(answerOptionTemplate.getAnswerOptionTemplate());
            answerOptionTemplateRepository.save(existing);
        } else {
            answerOptionTemplateRepository.save(answerOptionTemplate);
        }

        answerOptionTemplateRepository.flush();
    }

   public boolean deleteAnswerOptionTemplate(Integer id) {
        AnswerOptionTemplate template = answerOptionTemplateRepository.findById(id).orElse(null);
        if (template != null) {
            answerOptionTemplateRepository.delete(template);
            return true;
        }
        return false;
    }
    

}