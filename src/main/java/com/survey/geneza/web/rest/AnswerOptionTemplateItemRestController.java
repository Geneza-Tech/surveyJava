package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.AnswerOptionTemplateItem;
import com.survey.geneza.persistence.AnswerOptionTemplateItemRepository;
import com.survey.geneza.service.AnswerOptionTemplateItemService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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


@Controller("AnswerOptionTemplateItemRestController")
public class AnswerOptionTemplateItemRestController {

    @Autowired
    private AnswerOptionTemplateItemRepository answerOptionTemplateItemRepository;

    @Autowired
    private AnswerOptionTemplateItemService answerOptionTemplateItemService;

    @RequestMapping(value = "/AnswerOptionTemplateItem", method = RequestMethod.PUT)
    @ResponseBody
    public AnswerOptionTemplateItem saveAnswerOptionTemplateItem(@RequestBody AnswerOptionTemplateItem answerOptionTemplateItem) {
    answerOptionTemplateItemService.saveAnswerOptionTemplateItem(answerOptionTemplateItem);
        return answerOptionTemplateItemRepository.findById(answerOptionTemplateItem.getId());
    }

    @RequestMapping(value = "/AnswerOptionTemplateItem", method = RequestMethod.POST)
    @ResponseBody
    public AnswerOptionTemplateItem newAnswerOptionTemplateItem(@RequestBody AnswerOptionTemplateItem answerOptionTemplateItem) {
    answerOptionTemplateItemService.saveAnswerOptionTemplateItem(answerOptionTemplateItem);
        return answerOptionTemplateItemRepository.findById(answerOptionTemplateItem.getId());
    }

    @RequestMapping(value = "/AnswerOptionTemplateItem", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerOptionTemplateItem> listAnswerOptionTemplateItems() {
        return new java.util.ArrayList<AnswerOptionTemplateItem>(answerOptionTemplateItemService.findAll());
    }

    @RequestMapping(value = "/AnswerOptionTemplateItem/{answerOptionTemplateItem_id}", method = RequestMethod.GET)
    @ResponseBody
    public AnswerOptionTemplateItem loadAnswerOptionTemplateItem(@PathVariable Integer answerOptionTemplateItem_id) {
        return answerOptionTemplateItemService.findById(answerOptionTemplateItem_id);
    }

    @RequestMapping(value = "/AnswerOptionTemplateItem/Delete/{answerOptionTemplateItem_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteAnswerOptionTemplateItem(@PathVariable Integer answerOptionTemplateItem_id) {
        return answerOptionTemplateItemService.deleteAnswerOptionTemplateItem(answerOptionTemplateItem_id);
    }

 @RequestMapping(value = "/AnswerOptionTemplateItem/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<AnswerOptionTemplateItem> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (answerOptionTemplateItemRepository.findAll(sortedPaged));
    }


    @RequestMapping(value = "/AnswerOptionTemplateItem/AnswerOptionTemplate/{answerOptionTemplate_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerOptionTemplateItem> getAllByAnswerOptionTemplateId(@PathVariable("answerOptionTemplate_id") Integer answerOptionTemplateId) {
        return new java.util.ArrayList<AnswerOptionTemplateItem>(answerOptionTemplateItemService.findAllByAnswerOptionTemplateId(answerOptionTemplateId));
    }

}