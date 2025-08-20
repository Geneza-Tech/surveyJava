package com.survey.geneza.dto;

public class ResponseAnswersDTO {
    private Integer id;
    private String answerText;
    private Integer questionId;
    private Integer answerOptionId;
    private Integer responseId;

    public ResponseAnswersDTO() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerOptionId() {
        return answerOptionId;
    }
    public void setAnswerOptionId(Integer answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public Integer getResponseId() {
        return responseId;
    }
    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }
}
