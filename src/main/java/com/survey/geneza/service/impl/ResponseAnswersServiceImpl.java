package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ResponseAnswersRepository;
import com.survey.geneza.domain.ResponseAnswers;
import com.survey.geneza.service.ResponseAnswersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ResponseAnswersService")
@Transactional
public class ResponseAnswersServiceImpl implements ResponseAnswersService {

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

    

}