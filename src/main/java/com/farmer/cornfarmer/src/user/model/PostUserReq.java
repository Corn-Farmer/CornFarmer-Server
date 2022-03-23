package com.farmer.cornfarmer.src.user.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostUserReq {

    @NotNull
    @Size(min = 3, max = 6)
    private String nickname;

    private MultipartFile photo;
    private String is_male;
    private Date birth;
    private List<String> ottList;
    private List<String> genreList;

    public void setPhoto(MultipartFile photo) {
        if(photo.isEmpty())
        {
            this.photo = null;
        }
        else {
            this.photo = photo;
        }
    }
}
