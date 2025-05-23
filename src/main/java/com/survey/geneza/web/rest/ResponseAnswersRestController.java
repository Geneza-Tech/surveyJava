package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.ResponseAnswers;
import com.survey.geneza.persistence.ResponseAnswersRepository;
import com.survey.geneza.service.ResponseAnswersService;
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


@Controller("ResponseAnswersRestController")
public class ResponseAnswersRestController {

    @Autowired
    private ResponseAnswersRepository responseAnswersRepository;

    @Autowired
    private ResponseAnswersService responseAnswersService;

    @RequestMapping(value = "/ResponseAnswers", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseAnswers saveResponseAnswers(@RequestBody ResponseAnswers responseAnswers) {
    responseAnswersService.saveResponseAnswers(responseAnswers);
        return responseAnswersRepository.findById(responseAnswers.getId());
    }

    @RequestMapping(value = "/ResponseAnswers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseAnswers newResponseAnswers(@RequestBody ResponseAnswers responseAnswers) {
    responseAnswersService.saveResponseAnswers(responseAnswers);
        return responseAnswersRepository.findById(responseAnswers.getId());
    }

    @RequestMapping(value = "/ResponseAnswers", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseAnswers> listResponseAnswerss() {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersService.findAll());
    }

    @RequestMapping(value = "/ResponseAnswers/{responseAnswers_id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseAnswers loadResponseAnswers(@PathVariable Integer responseAnswers_id) {
        return responseAnswersService.findById(responseAnswers_id);
    }

    @RequestMapping(value = "/ResponseAnswers/Delete/{responseAnswers_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteResponseAnswers(@PathVariable Integer responseAnswers_id) {
        return responseAnswersService.deleteResponseAnswers(responseAnswers_id);
    }
    @RequestMapping(value = "/ResponseAnswers/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<ResponseAnswers> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (responseAnswersRepository.findAll(pageable));
    }

 @RequestMapping(value = "/ResponseAnswers/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<ResponseAnswers> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (responseAnswersRepository.findAll(sortedPaged));
    }


    @RequestMapping(value = "/ResponseAnswers/Question/{question_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseAnswers> getAllByQuestionId(@PathVariable("question_id") Integer questionId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersService.findAllByQuestionId(questionId));
    }

    @RequestMapping(value = "/ResponseAnswers/AnswerOption/{answerOption_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseAnswers> getAllByAnswerOptionId(@PathVariable("answerOption_id") Integer answerOptionId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersService.findAllByAnswerOptionId(answerOptionId));
    }

    @RequestMapping(value = "/ResponseAnswers/Response/{response_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseAnswers> getAllByResponseId(@PathVariable("response_id") Integer responseId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersService.findAllByResponseId(responseId));
    }

}