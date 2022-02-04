package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String oauth_id;
    private String nickname;
    private MultipartFile photo;
    private boolean is_male;
    private String birth;
    private List<Integer> ottList;
    private List<Integer> genreList;
}
