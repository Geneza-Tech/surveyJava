package com.survey.geneza.dto;

import java.util.Date;
import com.survey.geneza.domain.Response;

public class ResponseWithBatchDTO {
    private Response response;
    private String batch;
    private String location;
    private Date startDate;
    private Date endDate;

    public ResponseWithBatchDTO() {
    }

    public ResponseWithBatchDTO(Response response, String batch, String location, Date startDate, Date endDate) {
        this.response = response;
        this.batch = batch;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ResponseWithBatchDTO{" +
            "response=" + response +
            ", batch='" + batch + '\'' +
            ", location='" + location + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
