package com.survey.geneza.service.impl;
import com.survey.geneza.domain.GridDisplay;
import com.survey.geneza.service.GridDisplayService; 
import com.survey.geneza.domain.ResponseAnswers;
import com.survey.geneza.persistence.ResponseAnswersRepository;import com.survey.geneza.domain.Question;
import com.survey.geneza.persistence.QuestionRepository;import com.survey.geneza.domain.Participant;
import com.survey.geneza.persistence.ParticipantRepository;import com.survey.geneza.domain.QuestionType;
import com.survey.geneza.persistence.QuestionTypeRepository;import com.survey.geneza.domain.Survey;
import com.survey.geneza.persistence.SurveyRepository;import com.survey.geneza.domain.Response;
import com.survey.geneza.persistence.ResponseRepository;import com.survey.geneza.domain.Person;
import com.survey.geneza.persistence.PersonRepository;import com.survey.geneza.domain.AnswerOption;
import com.survey.geneza.persistence.AnswerOptionRepository;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("GridDisplayService")
@Transactional
public class GridDisplayServiceImpl implements GridDisplayService {

    public GridDisplayServiceImpl() {
    }
   
@Autowired
	private ResponseAnswersRepository responseAnswersRepository;@Autowired
	private QuestionRepository questionRepository;@Autowired
	private ParticipantRepository participantRepository;@Autowired
	private QuestionTypeRepository questionTypeRepository;@Autowired
	private SurveyRepository surveyRepository;@Autowired
	private ResponseRepository responseRepository;@Autowired
	private PersonRepository personRepository;@Autowired
	private AnswerOptionRepository answerOptionRepository;

    @Transactional
    public GridDisplay getListItems(String gridTag, Integer param1, Integer param2, Integer param3) {
       switch (gridTag){

       case "ResponseAnswers":{
				List<ResponseAnswers> responseAnswerss = responseAnswersRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(responseAnswerss);
			     return gridDisplay;	
			}case "Question":{
				List<Question> questions = questionRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(questions);
			     return gridDisplay;	
			}case "Participant":{
				List<Participant> participants = participantRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(participants);
			     return gridDisplay;	
			}case "QuestionType":{
				List<QuestionType> questionTypes = questionTypeRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(questionTypes);
			     return gridDisplay;	
			}case "Survey":{
				List<Survey> surveys = surveyRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(surveys);
			     return gridDisplay;	
			}case "Response":{
				List<Response> responses = responseRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(responses);
			     return gridDisplay;	
			}case "Person":{
				List<Person> persons = personRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(persons);
			     return gridDisplay;	
			}case "AnswerOption":{
				List<AnswerOption> answerOptions = answerOptionRepository.findAll();
				 GridDisplay gridDisplay = new GridDisplay(); 
			     gridDisplay.setContent(answerOptions);
			     return gridDisplay;	
			}     

       }
      return null;
    }

}