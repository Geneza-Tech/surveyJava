package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.AnswerOptionRepository;
import com.survey.geneza.persistence.QuestionRepository;
import com.survey.geneza.persistence.SurveyRepository;
import com.survey.geneza.domain.AnswerOption;
import com.survey.geneza.domain.Survey;
import com.survey.geneza.service.SurveyService;
import com.survey.geneza.domain.Question;
import java.util.Map;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SurveyService")
@Transactional
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;


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
                existingSurvey.setSurveyType(survey.getSurveyType()); // new line
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

     @Transactional
    public Survey duplicateSurvey(Integer surveyId, String surveyName) {
        Survey originalSurvey = surveyRepository.findById(surveyId);
        if (originalSurvey == null) {
            throw new RuntimeException("Survey not found with ID: " + surveyId);
        }

        Survey copySurvey = new Survey();
        copySurvey.setSurvey(surveyName);
        copySurvey.setSurveyType(originalSurvey.getSurveyType());
        surveyRepository.save(copySurvey);

        List<Question> originalQuestions = questionRepository.findAllBySurveyId(surveyId);
        Map<Integer, Question> questionMap = new HashMap<>();

        for (Question original : originalQuestions) {
            Question copy = new Question();
            copy.setSurvey(copySurvey);
            copy.setSection(original.getSection());
            copy.setText(original.getText());
            copy.setSurveyOrder(original.getSurveyOrder());
            copy.setChildOrder(original.getChildOrder());
            copy.setQuestionType(original.getQuestionType());
            questionRepository.save(copy);
            questionMap.put(original.getId(), copy);

            List<AnswerOption> options = answerOptionRepository.findAllByQuestionId(original.getId());
            for (AnswerOption option : options) {
                AnswerOption optionCopy = new AnswerOption();
                optionCopy.setQuestion(copy);
                optionCopy.setOptionValue(option.getOptionValue());
                optionCopy.setSequence(option.getSequence());
                answerOptionRepository.save(optionCopy);
            }
        }

        for (Question original : originalQuestions) {
            if (original.getParentQuestion() != null) {
                Question copy = questionMap.get(original.getId());
                Question parentCopy = questionMap.get(original.getParentQuestion().getId());
                copy.setParentQuestion(parentCopy);
                questionRepository.save(copy);
            }
        }

        return copySurvey;
    }

}