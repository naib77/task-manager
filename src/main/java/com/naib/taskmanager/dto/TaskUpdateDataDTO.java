package com.naib.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TaskUpdateDataDTO {
    @ApiModelProperty(position = 1, required = true, example = "This task is for demo purpose")
    private String description;
    @ApiModelProperty(position = 2, required = true, example = "open")
    private String status;
    @ApiModelProperty(position = 5)
    private Date due_date;
    @ApiModelProperty(position = 6)
    private Integer id;
}
