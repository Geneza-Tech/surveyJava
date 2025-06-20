package com.survey.geneza.service;
import com.survey.geneza.domain.Survey;
import java.util.List;

public interface SurveyService {
    public Survey findById(Integer id);
    public void saveSurvey(Survey survey_1);
    public boolean deleteSurvey(Integer surveyId);
    public List<Survey> findAll();
    Survey duplicateSurvey(Integer surveyId, String surveyName);
}