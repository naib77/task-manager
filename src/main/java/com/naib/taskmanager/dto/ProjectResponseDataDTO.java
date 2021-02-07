package com.naib.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProjectResponseDataDTO {
    @ApiModelProperty(position = 0, required = true)
    private String name;
    @ApiModelProperty(position = 1)
    private Integer user_id;
    @ApiModelProperty(position = 2)
    private Set<Integer> users;
    @ApiModelProperty(position = 3)
    private String message;
    @ApiModelProperty(position = 4)
    private String error;
}
