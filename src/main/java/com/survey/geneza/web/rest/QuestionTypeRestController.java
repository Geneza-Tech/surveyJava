package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.QuestionType;
import com.survey.geneza.persistence.QuestionTypeRepository;
import com.survey.geneza.service.QuestionTypeService;
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


@Controller("QuestionTypeRestController")
public class QuestionTypeRestController {

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private QuestionTypeService questionTypeService;

    @RequestMapping(value = "/QuestionType", method = RequestMethod.PUT)
    @ResponseBody
    public QuestionType saveQuestionType(@RequestBody QuestionType questionType) {
    questionTypeService.saveQuestionType(questionType);
        return questionTypeRepository.findById(questionType.getId());
    }

    @RequestMapping(value = "/QuestionType", method = RequestMethod.POST)
    @ResponseBody
    public QuestionType newQuestionType(@RequestBody QuestionType questionType) {
    questionTypeService.saveQuestionType(questionType);
        return questionTypeRepository.findById(questionType.getId());
    }

    @RequestMapping(value = "/QuestionType", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionType> listQuestionTypes() {
        return new java.util.ArrayList<QuestionType>(questionTypeService.findAll());
    }

    @RequestMapping(value = "/QuestionType/{questionType_id}", method = RequestMethod.GET)
    @ResponseBody
    public QuestionType loadQuestionType(@PathVariable Integer questionType_id) {
        return questionTypeService.findById(questionType_id);
    }

    @RequestMapping(value = "/QuestionType/Delete/{questionType_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteQuestionType(@PathVariable Integer questionType_id) {
        return questionTypeService.deleteQuestionType(questionType_id);
    }
    @RequestMapping(value = "/QuestionType/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<QuestionType> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (questionTypeRepository.findAll(pageable));
    }

 @RequestMapping(value = "/QuestionType/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<QuestionType> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (questionTypeRepository.findAll(sortedPaged));
    }


}