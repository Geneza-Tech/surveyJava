package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ResponseRepository;
import com.survey.geneza.domain.Response;
import com.survey.geneza.dto.ResponseWithBatchDTO;
import com.survey.geneza.service.ResponseService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
public void saveResponse(Response response) {

    Response existingResponse = responseRepository.findById(response.getId());
    
    if (existingResponse != null) {
        // Update all relevant fields
        existingResponse.setSurvey(response.getSurvey());
        existingResponse.setParticipant(response.getParticipant());
        existingResponse.setLinkType(response.getLinkType());
        existingResponse.setLinkId(response.getLinkId());
        existingResponse.setlinkcomment(response.getlinkcomment());
        existingResponse.setBatchId(response.getBatchId());
        existingResponse.setRole(response.getRole());
        existingResponse.setLanguage(response.getLanguage());

        responseRepository.save(existingResponse);
    } else {
        // Save new response
        responseRepository.save(response);
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

    // @Override
    // public List<Response> getResponsesByPersonId(Integer personId) {
    //     return responseRepository.findAllByPersonId(personId);
    // }

    @Override
public List<ResponseWithBatchDTO> getResponsesByPersonId(Integer personId) {
    List<Response> responses = responseRepository.findAllByPersonId(personId);
    List<ResponseWithBatchDTO> enriched = new ArrayList<>();

    for (Response r : responses) {
        Integer batchId = r.getBatchId(); // or however batch is linked

        Map<String, Object> batchInfo = new HashMap<>();
        if (batchId != null) {
            try {
                batchInfo = jdbcTemplate.queryForMap(
                    "SELECT batch, location,startdate,enddate FROM batch WHERE id = ?", batchId
                );
            } catch (Exception e) {
                // optional: log or skip batch info if not found
            }
        }

        ResponseWithBatchDTO dto = new ResponseWithBatchDTO();
        dto.setResponse(r);
        dto.setBatch((String) batchInfo.get("batch"));
        dto.setLocation((String) batchInfo.get("location"));
        dto.setStartDate((Date) batchInfo.get("startdate"));
        dto.setEndDate((Date) batchInfo.get("enddate"));

        enriched.add(dto);
    }

    return enriched;
}

    @Override
public List<Response> searchResponses(Integer batchId, Integer personId, Integer linkId,String role, Integer surveyId) {
    return responseRepository.searchResponses(batchId, personId, linkId,role, surveyId);
}

// @Override
// @Transactional(readOnly = true)
// public List<Long> getAllBatchIdsLinkedToPerson(Integer personId) {
//     List<BigInteger> batchIds = responseRepository.findAllBatchIdsLinkedToPerson(personId);
//     return batchIds.stream()
//                    .map(BigInteger::longValue)
//                    .collect(Collectors.toList());
// }

@Override
@Transactional(readOnly = true)
public List<Object[]> getAllBatchIdsLinkedToPerson(Integer personId) {
    return responseRepository.findAllBatchIdsLinkedToPerson(personId);
}

@Override
    public List<Response> findByParticipantAndLinkIdAndBatchId(Integer participant, Integer linkId, Integer batchId) {
        return responseRepository.findByParticipantAndLinkIdAndBatchId(participant, linkId, batchId);
    }


@Override
@Transactional
public List<Response> findAllByLanguageId(Integer languageId) {
    return responseRepository.findAllByLanguage_Id(languageId);
}


@Override
@Transactional
public List<Response> findAllByLanguageTempId(Long tempId) {
    return responseRepository.findAllByLanguage_TempId(tempId);
}

}