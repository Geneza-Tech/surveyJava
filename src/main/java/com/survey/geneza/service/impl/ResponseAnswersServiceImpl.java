package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ResponseAnswersRepository;
import com.survey.geneza.domain.AnswerOption;
import com.survey.geneza.domain.Question;
import com.survey.geneza.domain.Response;
import com.survey.geneza.domain.ResponseAnswers;
import com.survey.geneza.dto.ResponseAnswersDTO;
import com.survey.geneza.service.ResponseAnswersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ResponseAnswersService")
@Transactional
public class ResponseAnswersServiceImpl implements ResponseAnswersService {

    private AnswerOption answerOption;

    @Autowired
    private ResponseAnswersRepository responseAnswersRepository;
    public ResponseAnswersServiceImpl() {
    }

    @Transactional
    public ResponseAnswers findById(Integer id) {
        return responseAnswersRepository.findById(id);
    }

    @Transactional
    public List<ResponseAnswers> findAll() {
        return responseAnswersRepository.findAll();
    }

    
     
    @Transactional
    public void saveResponseAnswers(ResponseAnswers responseAnswers) {
        ResponseAnswers existingResponseAnswers = responseAnswersRepository.findById(responseAnswers.getId());
        if (existingResponseAnswers != null) {
        if (existingResponseAnswers != responseAnswers) {      
        existingResponseAnswers.setId(responseAnswers.getId());
                existingResponseAnswers.setQuestion(responseAnswers.getQuestion());
                existingResponseAnswers.setAnswerOption(responseAnswers.getAnswerOption());
                existingResponseAnswers.setResponse(responseAnswers.getResponse());
                existingResponseAnswers.setAnswerText(responseAnswers.getAnswerText());
        }
        responseAnswers = responseAnswersRepository.save(existingResponseAnswers);
    }else{
        responseAnswers = responseAnswersRepository.save(responseAnswers);
        }
        responseAnswersRepository.flush();
    }

    public boolean deleteResponseAnswers(Integer responseAnswersId) {
        ResponseAnswers responseAnswers = responseAnswersRepository.findById(responseAnswersId);
        if(responseAnswers!=null) {
            responseAnswersRepository.delete(responseAnswers);
            return true;
        }else {
            return false;
        }
    }@Transactional
    public List<ResponseAnswers> findAllByQuestionId(Integer  questionId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersRepository.findAllByQuestionId(questionId));
    }@Transactional
    public List<ResponseAnswers> findAllByAnswerOptionId(Integer  answerOptionId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersRepository.findAllByAnswerOptionId(answerOptionId));
    }@Transactional
    public List<ResponseAnswers> findAllByResponseId(Integer  responseId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersRepository.findAllByResponseId(responseId));
    }

    @Override
@Transactional
public void bulkSaveForResponse(Integer responseId, List<ResponseAnswersDTO> answers) {
    if (answers == null || answers.isEmpty()) {
        return;
    }

    // Remove duplicates by questionId from the incoming list
    Map<Integer, ResponseAnswersDTO> uniqueAnswers = answers.stream()
            .collect(Collectors.toMap(
                    ResponseAnswersDTO::getQuestionId,
                    a -> a,
                    (a1, a2) -> a1 // keep first if duplicate
            ));

    // Delete existing answers for the same response & question IDs
    List<Integer> questionIds = new ArrayList<>(uniqueAnswers.keySet());
    if (!questionIds.isEmpty()) {
        responseAnswersRepository.deleteByResponseIdAndQuestionIds(responseId, questionIds);
    }

    // Convert DTOs to entities
    List<ResponseAnswers> entities = uniqueAnswers.values().stream().map(dto -> {
        ResponseAnswers entity = new ResponseAnswers();

        // set Response (assuming Response has a constructor with id)
        Response response = new Response();
        response.setId(responseId);
        entity.setResponse(response);

        // set Question
        Question question = new Question();
        question.setId(dto.getQuestionId());
        entity.setQuestion(question);

        // set AnswerOption if provided
        if (dto.getAnswerOptionId() != null) {
            AnswerOption answerOption = new AnswerOption();
            answerOption.setId(dto.getAnswerOptionId());
            entity.setAnswerOption(answerOption);
        }

        // set AnswerText
        entity.setAnswerText(dto.getAnswerText());

        return entity;
    }).collect(Collectors.toList());

    // Bulk save
    if (!entities.isEmpty()) {
        responseAnswersRepository.saveAll(entities);
    }
}



}