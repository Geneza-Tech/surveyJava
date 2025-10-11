package com.survey.geneza.dto;

import javax.validation.constraints.NotNull;

public class ResponseAnswersDTO {

    @NotNull(message = "Question ID is required")
    private Integer questionId;

    @NotNull(message = "Response ID is required")
    private Integer responseId;

    // For single answer questions
    private Integer answerOptionId;

    // For multiple answer questions - comma-separated IDs
    private String multipleAnswerOptions;

    // For text/open-ended questions
    private String answerText;

    public ResponseAnswersDTO() {
    }

    public ResponseAnswersDTO(Integer questionId, Integer responseId, Integer answerOptionId, 
                              String multipleAnswerOptions, String answerText) {
        this.questionId = questionId;
        this.responseId = responseId;
        this.answerOptionId = answerOptionId;
        this.multipleAnswerOptions = multipleAnswerOptions;
        this.answerText = answerText;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }

    public Integer getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(Integer answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public String getMultipleAnswerOptions() {
        return multipleAnswerOptions;
    }

    public void setMultipleAnswerOptions(String multipleAnswerOptions) {
        this.multipleAnswerOptions = multipleAnswerOptions;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}