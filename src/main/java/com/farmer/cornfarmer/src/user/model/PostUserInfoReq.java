package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUserInfoReq {
    String nickname;
    MultipartFile photo;
    List<Integer> userOtt;
    List<Integer> genreList;
 }
