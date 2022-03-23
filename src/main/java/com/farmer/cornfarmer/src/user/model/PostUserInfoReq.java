package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUserInfoReq {

    @NotNull
    @Size(min = 3, max = 6)
    String nickname;

    MultipartFile photo;
    List<String> ottList;
    List<String> genreList;
}
