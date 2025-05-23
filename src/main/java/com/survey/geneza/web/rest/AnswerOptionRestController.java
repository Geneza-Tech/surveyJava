package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.AnswerOption;
import com.survey.geneza.domain.AnswerOptionTemplate;
import com.survey.geneza.domain.AnswerOptionTemplateItem;
import com.survey.geneza.domain.Question;
import com.survey.geneza.persistence.AnswerOptionRepository;
import com.survey.geneza.persistence.AnswerOptionTemplateItemRepository;
import com.survey.geneza.persistence.AnswerOptionTemplateRepository;
import com.survey.geneza.persistence.QuestionRepository;
import com.survey.geneza.service.AnswerOptionService;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


@Controller("AnswerOptionRestController")
public class AnswerOptionRestController {

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private AnswerOptionTemplateItemRepository answerOptionTemplateItemRepository;

    @Autowired
    private AnswerOptionService answerOptionService;

    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping(value = "/AnswerOption", method = RequestMethod.PUT)
    @ResponseBody
    public AnswerOption saveAnswerOption(@RequestBody AnswerOption answerOption) {
    answerOptionService.saveAnswerOption(answerOption);
        return answerOptionRepository.findById(answerOption.getId());
    }

    @RequestMapping(value = "/AnswerOption", method = RequestMethod.POST)
    @ResponseBody
    public AnswerOption newAnswerOption(@RequestBody AnswerOption answerOption) {
    answerOptionService.saveAnswerOption(answerOption);
        return answerOptionRepository.findById(answerOption.getId());
    }

    @RequestMapping(value = "/AnswerOption", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerOption> listAnswerOptions() {
        return new java.util.ArrayList<AnswerOption>(answerOptionService.findAll());
    }

    @RequestMapping(value = "/AnswerOption/{answerOption_id}", method = RequestMethod.GET)
    @ResponseBody
    public AnswerOption loadAnswerOption(@PathVariable Integer answerOption_id) {
        return answerOptionService.findById(answerOption_id);
    }

    @RequestMapping(value = "/AnswerOption/Delete/{answerOption_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteAnswerOption(@PathVariable Integer answerOption_id) {
        return answerOptionService.deleteAnswerOption(answerOption_id);
    }
    @RequestMapping(value = "/AnswerOption/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<AnswerOption> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (answerOptionRepository.findAll(pageable));
    }

 @RequestMapping(value = "/AnswerOption/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<AnswerOption> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (answerOptionRepository.findAll(sortedPaged));
    }


    @RequestMapping(value = "/AnswerOption/Question/{question_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerOption> getAllByQuestionId(@PathVariable("question_id") Integer questionId) {
        return new java.util.ArrayList<AnswerOption>(answerOptionService.findAllByQuestionId(questionId));
    }

    @RequestMapping(value = "/AnswerOption/{templateId}/ToQuestion/{questionId}", method = RequestMethod.POST)
@ResponseBody
public void createAnswerOptionsFromTemplate(Integer templateId, Integer questionId) {
    List<AnswerOptionTemplateItem> items = answerOptionTemplateItemRepository.findByAnswerOptionTemplate_Id(templateId);
    Question question = questionRepository.findById(questionId).orElse(null);

     int sequenceCounter = 1;

    for (AnswerOptionTemplateItem item : items) {
        AnswerOption answerOption = new AnswerOption();
        answerOption.setOptionValue(item.getAnswerOption());
        answerOption.setSequence(sequenceCounter++);
        answerOption.setQuestion(question);
        answerOptionRepository.save(answerOption);
    }
}


}