package com.farmer.cornfarmer.src.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLoginReq {
    private String accessToken;
    public PostLoginReq(@JsonProperty("accessToken") String accessToken)
    {
        this.accessToken = accessToken;
  }
}
