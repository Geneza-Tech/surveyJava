package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.Response;
import com.survey.geneza.persistence.ResponseRepository;
import com.survey.geneza.service.ResponseService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;


@Controller("ResponseRestController")
public class ResponseRestController {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ResponseService responseService;

    @RequestMapping(value = "/Response", method = RequestMethod.PUT)
    @ResponseBody
    public Response saveResponse(@RequestBody Response response) {
    responseService.saveResponse(response);
        return responseRepository.findById(response.getId());
    }

    @RequestMapping(value = "/Response", method = RequestMethod.POST)
    @ResponseBody
    public Response newResponse(@RequestBody Response response) {
    responseService.saveResponse(response);
        return responseRepository.findById(response.getId());
    }

    @RequestMapping(value = "/Response", method = RequestMethod.GET)
    @ResponseBody
    public List<Response> listResponses() {
        return new java.util.ArrayList<Response>(responseService.findAll());
    }

    @RequestMapping(value = "/Response/{response_id}", method = RequestMethod.GET)
    @ResponseBody
    public Response loadResponse(@PathVariable Integer response_id) {
        return responseService.findById(response_id);
    }

    @RequestMapping(value = "/Response/Delete/{response_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteResponse(@PathVariable Integer response_id) {
        return responseService.deleteResponse(response_id);
    }
    @RequestMapping(value = "/Response/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Response> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (responseRepository.findAll(pageable));
    }

 @RequestMapping(value = "/Response/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Response> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (responseRepository.findAll(sortedPaged));
    }


    @RequestMapping(value = "/Response/Survey/{survey_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Response> getAllBySurveyId(@PathVariable("survey_id") Integer surveyId) {
        return new java.util.ArrayList<Response>(responseService.findAllBySurveyId(surveyId));
    }

    @RequestMapping(value = "/Response/Participant/{participant_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Response> getAllByParticipantId(@PathVariable("participant_id") Integer participantId) {
        return new java.util.ArrayList<Response>(responseService.findAllByParticipantId(participantId));
    }

    @RequestMapping(value = "/Response/Survey/{survey_id}/Question/{question_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Response> getResponsesBySurveyAndQuestion(
        @PathVariable("survey_id") Integer surveyId,
        @PathVariable("question_id") Integer questionId) {
    return responseService.findAllBySurveyIdAndQuestionId(surveyId, questionId);
}

@RequestMapping(value = "/Response/bylink", method = RequestMethod.GET)
    @ResponseBody
    public List<Response> getByLinkTypeAndLinkId(
            @RequestParam("linkType") String linkType,
            @RequestParam("linkId") Integer linkId) {
        return responseService.findByLinkTypeAndLinkId(linkType, linkId);
    }

    @RequestMapping(value = "/Response/personId/{personId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Response>> getResponsesByPersonId(@PathVariable Integer personId) {
        List<Response> responses = responseService.getResponsesByPersonId(personId);
        return ResponseEntity.ok(responses);
    }

    @RequestMapping(value = "/Response/linkIdBatchId", method = RequestMethod.GET)
    @ResponseBody
    public List<Response> findByBatchIdAndLinkId(
            @RequestParam("batchId") String batchId,
            @RequestParam("linkId") Integer linkId) {
        return responseService.findByBatchIdAndLinkId(batchId, linkId);
    }

    @RequestMapping(value = "/Response/search", method = RequestMethod.GET)
@ResponseBody
public List<Response> searchResponses(
        @RequestParam(value = "batchId", required = false) Integer batchId,
        @RequestParam(value = "personId", required = false) Integer personId,
        @RequestParam(value = "linkId", required = false) Integer linkId,
        @RequestParam(value = "surveyId", required = false) Integer surveyId) {
    return responseService.searchResponses(batchId, personId, linkId, surveyId);
}


}