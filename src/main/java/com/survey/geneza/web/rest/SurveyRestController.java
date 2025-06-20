package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.Survey;
import com.survey.geneza.persistence.SurveyRepository;
import com.survey.geneza.service.SurveyService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


@RestController("SurveyRestController")
public class SurveyRestController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyService surveyService;

    @RequestMapping(value = "/Survey", method = RequestMethod.PUT)
    @ResponseBody
    public Survey saveSurvey(@RequestBody Survey survey) {
    surveyService.saveSurvey(survey);
        return surveyRepository.findById(survey.getId());
    }

    @RequestMapping(value = "/Survey", method = RequestMethod.POST)
    @ResponseBody
    public Survey newSurvey(@RequestBody Survey survey) {
    surveyService.saveSurvey(survey);
        return surveyRepository.findById(survey.getId());
    }

    @RequestMapping(value = "/Survey", method = RequestMethod.GET)
    @ResponseBody
    public List<Survey> listSurveys() {
        return new java.util.ArrayList<Survey>(surveyService.findAll());
    }

    @RequestMapping(value = "/Survey/{survey_id}", method = RequestMethod.GET)
    @ResponseBody
    public Survey loadSurvey(@PathVariable Integer survey_id) {
        return surveyService.findById(survey_id);
    }

    @RequestMapping(value = "/Survey/Delete/{survey_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteSurvey(@PathVariable Integer survey_id) {
        return surveyService.deleteSurvey(survey_id);
    }
    @RequestMapping(value = "/Survey/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Survey> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (surveyRepository.findAll(pageable));
    }

 @RequestMapping(value = "/Survey/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Survey> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (surveyRepository.findAll(sortedPaged));
    }

@RequestMapping(value = "/Survey//Duplicate/{id}", method = RequestMethod.POST)
@ResponseBody
public Survey duplicateSurvey(@PathVariable Integer id, @RequestParam String name) {
    return surveyService.duplicateSurvey(id,name);
}


}