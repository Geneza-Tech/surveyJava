package com.survey.geneza.service;
import com.survey.geneza.domain.Survey;
import java.util.List;

public interface SurveyService {
    public Survey findById(Integer id);
    public void saveSurvey(Survey survey_1);
    public boolean deleteSurvey(Integer surveyId);
    public List<Survey> findAll();
    Survey duplicateSurvey(Integer surveyId, String surveyName);
    List<Survey> findBySurveyType(String surveyType);
    List<Survey> findBySurveyrole(String surveyrole);
    List<Survey> findBySurveyTypeAndSurveyrole(String surveyType, String surveyrole);
}