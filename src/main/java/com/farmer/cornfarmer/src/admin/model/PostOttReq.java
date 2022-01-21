package com.farmer.cornfarmer.src.admin.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostOttReq {
    private String ottName;
    private MultipartFile ottPhoto;
}
