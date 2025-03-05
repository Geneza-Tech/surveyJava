package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.Question;
import com.survey.geneza.persistence.QuestionRepository;
import com.survey.geneza.service.QuestionService;
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


@Controller("QuestionRestController")
public class QuestionRestController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/Question", method = RequestMethod.PUT)
    @ResponseBody
    public Question saveQuestion(@RequestBody Question question) {
    questionService.saveQuestion(question);
        return questionRepository.findById(question.getId());
    }

    @RequestMapping(value = "/Question", method = RequestMethod.POST)
    @ResponseBody
    public Question newQuestion(@RequestBody Question question) {
    questionService.saveQuestion(question);
        return questionRepository.findById(question.getId());
    }

    @RequestMapping(value = "/Question", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> listQuestions() {
        return new java.util.ArrayList<Question>(questionService.findAll());
    }

    @RequestMapping(value = "/Question/{question_id}", method = RequestMethod.GET)
    @ResponseBody
    public Question loadQuestion(@PathVariable Integer question_id) {
        return questionService.findById(question_id);
    }

    @RequestMapping(value = "/Question/Delete/{question_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteQuestion(@PathVariable Integer question_id) {
        return questionService.deleteQuestion(question_id);
    }
    @RequestMapping(value = "/Question/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Question> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (questionRepository.findAll(pageable));
    }

 @RequestMapping(value = "/Question/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Question> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (questionRepository.findAll(sortedPaged));
    }


    @RequestMapping(value = "/Question/Survey/{survey_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getAllBySurveyId(@PathVariable("survey_id") Integer surveyId) {
        return new java.util.ArrayList<Question>(questionService.findAllBySurveyId(surveyId));
    }

    @RequestMapping(value = "/Question/QuestionType/{questionType_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getAllByQuestionTypeId(@PathVariable("questionType_id") Integer questionTypeId) {
        return new java.util.ArrayList<Question>(questionService.findAllByQuestionTypeId(questionTypeId));
    }

    @RequestMapping(value = "/Question/ParentQuestion/{parentQuestion_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getAllByParentQuestionId(@PathVariable("parentQuestion_id") Integer parentQuestionId) {
        return new java.util.ArrayList<Question>(questionService.findAllByParentQuestionId(parentQuestionId));
    }

}