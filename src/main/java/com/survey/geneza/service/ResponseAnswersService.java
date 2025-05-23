package com.survey.geneza.service;
import com.survey.geneza.domain.ResponseAnswers;
import java.util.List;

public interface ResponseAnswersService {
    public ResponseAnswers findById(Integer id);
    public void saveResponseAnswers(ResponseAnswers responseAnswers_1);
    public boolean deleteResponseAnswers(Integer responseAnswersId);
    public List<ResponseAnswers> findAll();
    public List<ResponseAnswers> findAllByQuestionId(Integer  question);
    public List<ResponseAnswers> findAllByAnswerOptionId(Integer  answerOption);
    public List<ResponseAnswers> findAllByResponseId(Integer  response);
}