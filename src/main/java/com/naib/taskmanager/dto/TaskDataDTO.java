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
public class TaskDataDTO {
    @ApiModelProperty(position = 0, required = true)
    private String description;
    @ApiModelProperty(position = 1, required = true)
    private String status;
    @ApiModelProperty(position = 2, required = true)
    private Integer project_id;
    @ApiModelProperty(position = 3)
    private Integer user_id;
    @ApiModelProperty(position = 4)
    private Date due_date;
    @ApiModelProperty(position = 5)
    private Integer id;
}
