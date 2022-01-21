package com.farmer.cornfarmer.src.admin.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMovieReq {

    @NotNull
    private String movieTitle;

    @NotNull
    private String releaseYear;

    @NotNull
    private String synopsis;

    @NotNull
    private String director;

    @NotEmpty
    private List<String> genreList;

    @NotEmpty
    private List<MultipartFile> moviePhoto;

    @NotEmpty
    private List<String> ottList;
}
