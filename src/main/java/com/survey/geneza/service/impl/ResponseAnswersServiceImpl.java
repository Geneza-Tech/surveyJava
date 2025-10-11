package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ResponseAnswersRepository;
import com.survey.geneza.persistence.AnswerOptionRepository;
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

    @Autowired
    private ResponseAnswersRepository responseAnswersRepository;
    
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

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

    /**
     * Validates multiple answer options (comma-separated IDs)
     * @param multipleAnswerOptions comma-separated answer option IDs
     * @param questionId the question ID these options should belong to
     * @throws IllegalArgumentException if validation fails
     */
    private void validateMultipleAnswerOptions(String multipleAnswerOptions, Integer questionId) {
        if (multipleAnswerOptions == null || multipleAnswerOptions.trim().isEmpty()) {
            return;
        }

        String[] answerOptionIds = multipleAnswerOptions.split(",");
        
        for (String idStr : answerOptionIds) {
            try {
                Integer id = Integer.parseInt(idStr.trim());
                AnswerOption answerOption = answerOptionRepository.findById(id);
                
                if (answerOption == null) {
                    throw new IllegalArgumentException("Answer option with ID " + id + " not found");
                }

                // Validate that the answer option belongs to the specified question
                // if (questionId != null && !answerOption.getQuestion().getId().equals(questionId)) {
                //     throw new IllegalArgumentException(
                //         "Answer option with ID " + id + " does not belong to question " + questionId
                //     );
                // }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid answer option ID format: " + idStr.trim());
            }
        }
    }

    /**
     * Validates a single answer option
     * @param answerOptionId the answer option ID
     * @param questionId the question ID this option should belong to
     * @throws IllegalArgumentException if validation fails
     */
    private void validateSingleAnswerOption(Integer answerOptionId, Integer questionId) {
        if (answerOptionId == null) {
            return;
        }

        AnswerOption answerOption = answerOptionRepository.findById(answerOptionId);
        
        if (answerOption == null) {
            throw new IllegalArgumentException("Answer option with ID " + answerOptionId + " not found");
        }

        // Validate that the answer option belongs to the specified question
        // if (questionId != null && !answerOption.getQuestion().getId().equals(questionId)) {
        //     throw new IllegalArgumentException(
        //         "Answer option with ID " + answerOptionId + " does not belong to question " + questionId
        //     );
        // }
    }
     
    @Transactional
    public void saveResponseAnswers(ResponseAnswers responseAnswers) {
        // Validate multipleAnswerOptions if present
        if (responseAnswers.getMultipleAnswerOptions() != null && 
            !responseAnswers.getMultipleAnswerOptions().trim().isEmpty()) {
            Integer questionId = responseAnswers.getQuestion() != null ? 
                responseAnswers.getQuestion().getId() : null;
            validateMultipleAnswerOptions(responseAnswers.getMultipleAnswerOptions(), questionId);
        }

        // Validate single answerOption if present
        if (responseAnswers.getAnswerOption() != null) {
            Integer questionId = responseAnswers.getQuestion() != null ? 
                responseAnswers.getQuestion().getId() : null;
            validateSingleAnswerOption(responseAnswers.getAnswerOption().getId(), questionId);
        }

        ResponseAnswers existingResponseAnswers = responseAnswersRepository.findById(responseAnswers.getId());
        if (existingResponseAnswers != null) {
            if (existingResponseAnswers != responseAnswers) {      
                existingResponseAnswers.setId(responseAnswers.getId());
                existingResponseAnswers.setQuestion(responseAnswers.getQuestion());
                existingResponseAnswers.setAnswerOption(responseAnswers.getAnswerOption());
                existingResponseAnswers.setResponse(responseAnswers.getResponse());
                existingResponseAnswers.setAnswerText(responseAnswers.getAnswerText());
                existingResponseAnswers.setMultipleAnswerOptions(responseAnswers.getMultipleAnswerOptions());
            }
            responseAnswers = responseAnswersRepository.save(existingResponseAnswers);
        } else {
            responseAnswers = responseAnswersRepository.save(responseAnswers);
        }
        responseAnswersRepository.flush();
    }

    public boolean deleteResponseAnswers(Integer responseAnswersId) {
        ResponseAnswers responseAnswers = responseAnswersRepository.findById(responseAnswersId);
        if(responseAnswers != null) {
            responseAnswersRepository.delete(responseAnswers);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public List<ResponseAnswers> findAllByQuestionId(Integer questionId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersRepository.findAllByQuestionId(questionId));
    }

    @Transactional
    public List<ResponseAnswers> findAllByAnswerOptionId(Integer answerOptionId) {
        return new java.util.ArrayList<ResponseAnswers>(responseAnswersRepository.findAllByAnswerOptionId(answerOptionId));
    }

    @Transactional
    public List<ResponseAnswers> findAllByResponseId(Integer responseId) {
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

        // Validate all answers before any database operations
        for (ResponseAnswersDTO dto : uniqueAnswers.values()) {
            // Validate multipleAnswerOptions if present
            if (dto.getMultipleAnswerOptions() != null && 
                !dto.getMultipleAnswerOptions().trim().isEmpty()) {
                validateMultipleAnswerOptions(dto.getMultipleAnswerOptions(), dto.getQuestionId());
            }

            // Validate single answerOption if present
            if (dto.getAnswerOptionId() != null) {
                validateSingleAnswerOption(dto.getAnswerOptionId(), dto.getQuestionId());
            }
        }

        // Delete existing answers for the same response & question IDs
        List<Integer> questionIds = new ArrayList<>(uniqueAnswers.keySet());
        if (!questionIds.isEmpty()) {
            responseAnswersRepository.deleteByResponseIdAndQuestionIds(responseId, questionIds);
        }

        // Convert DTOs to entities
        List<ResponseAnswers> entities = uniqueAnswers.values().stream().map(dto -> {
            ResponseAnswers entity = new ResponseAnswers();

            // set Response
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

            // set MultipleAnswerOptions
            if (dto.getMultipleAnswerOptions() != null) {
                entity.setMultipleAnswerOptions(dto.getMultipleAnswerOptions());
            }

            return entity;
        }).collect(Collectors.toList());

        // Bulk save
        if (!entities.isEmpty()) {
            responseAnswersRepository.saveAll(entities);
        }
    }
}