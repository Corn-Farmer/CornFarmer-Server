package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserMyInfo {
    private String nickname;
    private String photo;
    private Boolean is_male;
    private Date Date;
    private List<OttInfo> ottList;
    private List<GenreInfo> genreList;

}


