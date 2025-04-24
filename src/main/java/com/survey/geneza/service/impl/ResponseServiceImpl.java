package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ResponseRepository;
import com.survey.geneza.domain.Response;
import com.survey.geneza.service.ResponseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ResponseService")
@Transactional
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseRepository responseRepository;
    public ResponseServiceImpl() {
    }

    @Transactional
    public Response findById(Integer id) {
        return responseRepository.findById(id);
    }

    @Transactional
    public List<Response> findAll() {
        return responseRepository.findAll();
    }
     
    @Transactional
    public void saveResponse(Response response) {
        Response existingResponse = responseRepository.findById(response.getId());
        if (existingResponse != null) {
        if (existingResponse != response) {      
        existingResponse.setId(response.getId());
                existingResponse.setSurvey(response.getSurvey());
                existingResponse.setParticipant(response.getParticipant());
                existingResponse.setLinkType(response.getLinkType()); // new line
                existingResponse.setLinkId(response.getLinkId());
                existingResponse.setlinkcomment(response.getlinkcomment());
        }
        response = responseRepository.save(existingResponse);
    }else{
        response = responseRepository.save(response);
        }
        responseRepository.flush();
    }

    public boolean deleteResponse(Integer responseId) {
        Response response = responseRepository.findById(responseId);
        if(response!=null) {
            responseRepository.delete(response);
            return true;
        }else {
            return false;
        }
    }@Transactional
    public List<Response> findAllBySurveyId(Integer  surveyId) {
        return new java.util.ArrayList<Response>(responseRepository.findAllBySurveyId(surveyId));
    }@Transactional
    public List<Response> findAllByParticipantId(Integer  participantId) {
        return new java.util.ArrayList<Response>(responseRepository.findAllByParticipantId(participantId));
    } @Transactional
    public List<Response> findAllBySurveyIdAndQuestionId(Integer surveyId, Integer questionId) {
        return responseRepository.findAllBySurveyIdAndQuestionId(surveyId, questionId);
    }
    @Transactional
    public List<Response> findByLinkTypeAndLinkId(String linkType, Integer linkId) {
        return responseRepository.findByLinkTypeAndLinkId(linkType, linkId);
    }

    @Transactional
    public List<Response> findByBatchIdAndLinkId(String batchId, Integer linkId) {
        return responseRepository.findByBatchIdAndLinkId(batchId, linkId);
    }

    @Override
    public List<Response> getResponsesByPersonId(Integer personId) {
        return responseRepository.findAllByPersonId(personId);
    }


}