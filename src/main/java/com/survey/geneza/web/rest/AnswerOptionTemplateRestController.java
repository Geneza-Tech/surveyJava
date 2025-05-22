package com.survey.geneza.web.rest;

import com.survey.geneza.domain.AnswerOptionTemplate;
import com.survey.geneza.persistence.AnswerOptionTemplateRepository;
import com.survey.geneza.service.AnswerOptionTemplateService;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Controller("AnswerOptionTemplateRestController")
public class AnswerOptionTemplateRestController {

    @Autowired
    private AnswerOptionTemplateRepository answerOptionTemplateRepository;

    @Autowired
    private AnswerOptionTemplateService answerOptionTemplateService;

    @RequestMapping(value = "/AnswerOptionTemplate", method = RequestMethod.PUT)
@ResponseBody
public AnswerOptionTemplate saveAnswerOptionTemplate(@RequestBody AnswerOptionTemplate answerOptionTemplate) {
    answerOptionTemplateService.saveAnswerOptionTemplate(answerOptionTemplate);
    return answerOptionTemplateRepository.findById(answerOptionTemplate.getId()).orElse(null);
}

@RequestMapping(value = "/AnswerOptionTemplate", method = RequestMethod.POST)
@ResponseBody
public AnswerOptionTemplate newAnswerOptionTemplate(@RequestBody AnswerOptionTemplate answerOptionTemplate) {
    answerOptionTemplateService.saveAnswerOptionTemplate(answerOptionTemplate);
    return answerOptionTemplateRepository.findById(answerOptionTemplate.getId()).orElse(null);
}


    @RequestMapping(value = "/AnswerOptionTemplate", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerOptionTemplate> listAnswerOptionTemplates() {
        return answerOptionTemplateService.findAll();
    }

    @RequestMapping(value = "/AnswerOptionTemplate/{answerOptionTemplate_id}", method = RequestMethod.GET)
    @ResponseBody
    public AnswerOptionTemplate loadAnswerOptionTemplate(@PathVariable Integer answerOptionTemplate_id) {
        return answerOptionTemplateService.findById(answerOptionTemplate_id);
    }

    @RequestMapping(value = "/AnswerOptionTemplate/Delete/{answerOptionTemplate_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteAnswerOptionTemplate(@PathVariable Integer answerOptionTemplate_id) {
        return answerOptionTemplateService.deleteAnswerOptionTemplate(answerOptionTemplate_id);
    }

    @RequestMapping(value = "/AnswerOptionTemplate/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<AnswerOptionTemplate> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
        Sort sort = (direction == 1) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable sortedPaged = PageRequest.of(page, 10, sort);
        return answerOptionTemplateRepository.findAll(sortedPaged);
    }

    
}
