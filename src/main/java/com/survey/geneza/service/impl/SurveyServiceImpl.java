package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.SurveyRepository;
import com.survey.geneza.domain.Survey;
import com.survey.geneza.service.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SurveyService")
@Transactional
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    public SurveyServiceImpl() {
    }

    @Transactional
    public Survey findById(Integer id) {
        return surveyRepository.findById(id);
    }

    @Transactional
    public List<Survey> findAll() {
        return surveyRepository.findAll();
    }
     
    @Transactional
    public void saveSurvey(Survey survey) {
        Survey existingSurvey = surveyRepository.findById(survey.getId());
        if (existingSurvey != null) {
        if (existingSurvey != survey) {      
        existingSurvey.setId(survey.getId());
                existingSurvey.setSurvey(survey.getSurvey());
        }
        survey = surveyRepository.save(existingSurvey);
    }else{
        survey = surveyRepository.save(survey);
        }
        surveyRepository.flush();
    }

    public boolean deleteSurvey(Integer surveyId) {
        Survey survey = surveyRepository.findById(surveyId);
        if(survey!=null) {
            surveyRepository.delete(survey);
            return true;
        }else {
            return false;
        }
    }

    

}