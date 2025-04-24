package com.survey.geneza.service;
import com.survey.geneza.domain.Response;
import java.util.List;

public interface ResponseService {
    public Response findById(Integer id);
    public void saveResponse(Response response_1);
    public boolean deleteResponse(Integer responseId);
    public List<Response> findAll();
    public List<Response> findAllBySurveyId(Integer  survey);
    public List<Response> findAllByParticipantId(Integer  participant);
    public List<Response> findAllBySurveyIdAndQuestionId(Integer surveyId, Integer questionId);
    public List<Response> findByLinkTypeAndLinkId(String linkType, Integer linkId);
    public List<Response> findByBatchIdAndLinkId(String batchId, Integer linkId);
    List<Response> getResponsesByPersonId(Integer personId);


}