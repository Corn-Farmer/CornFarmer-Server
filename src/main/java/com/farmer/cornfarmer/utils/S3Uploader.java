package com.farmer.cornfarmer.utils;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import static com.farmer.cornfarmer.config.BaseResponseStatus.FILE_CONVERT_ERROR;
import static com.farmer.cornfarmer.config.BaseResponseStatus.FILE_DELETE_ERROR;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws BaseException {
        /**
         * controller에서 호출하는 함수
         * 1. MultipartFile을 file로 변환(이 과정에서 로컬에 file 자동생성됨)
         * 2. 변환된 file을 S3에 저장(put)
         * 3. 로컬에 저장된 파일을 제거
         */
        try {
            File uploadFile = convert(multipartFile).orElseThrow(() -> new BaseException(FILE_CONVERT_ERROR));
            return upload(uploadFile, dirName);
        } catch (IOException e) {
            System.out.println(e);
            throw new BaseException(FILE_CONVERT_ERROR);
        }
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        //S3에 file 업로드(put)
        String uploadImageUrl = putS3(fileName, uploadFile);
        //로컬에 자동 생성된 file 삭제
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(String fileName, File uploadFile) {
        /**
         * fileName : s3에 저장할 이름
         * uploadFile : s3에 저장할 파일
         */
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public Boolean isPhotoExist(String filePath) throws  BaseException{
        System.out.println(filePath);
        boolean isExistObject = amazonS3Client.doesObjectExist(bucket, filePath);
        return isExistObject;
    }

    public void delete(String filePath) throws BaseException {
        try {
            System.out.println(filePath);
            amazonS3Client.deleteObject(bucket, filePath);
        }catch (Exception e)
        {
            System.out.println(e);
            throw new BaseException(FILE_DELETE_ERROR);
        }
    }
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            logger.info("파일이 삭제되었습니다.");
        } else {
            logger.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        // Multipartfile에서 File로 전환
        // 전환되는 과정에서 로컬에 파일이 생성됨
        //File convertFile = new File("image/" + file.getOriginalFilename());
        File convertFile = new File("C:/Users/wheog/OneDrive/바탕 화면/조대환/CornFarmer/image/" + file.getOriginalFilename()); //변환경로

        System.out.println(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
