package com.url.shortner.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Request object for POST method")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto {

    @ApiModelProperty(required = true, notes = "Url to be converted")
    private String longUrl;
}
