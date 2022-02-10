package com.farmer.cornfarmer.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    POST_USERS_KAKAO_ERROR(false, 2011, "카카오 로그인에 실패했습니다."),
    POST_USERS_NAVER_ERROR(false, 2012, "네이버 로그인에 실패했습니다."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_INVALID_NICKNAME(false, 2018, "닉네임이 디비에 존재하지 않습니다."),
    POST_USERS_INVALID_OATUH_ID(false, 2019, "oauth_id가 디비에 없습니다."),
    POST_USERS_CREATE_FAILED(false, 2020, "db 유저생성을 실패헸습니다."),
    POST_USERS_EXIST_OAUTHID(false, 2021, "db에 유저가 이미 존재합니다.."),




    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    DUPLICATE_NICKNAME(false, 3015, "중복된 닉네임 입니다."),
    // [DELETE] /reviews/{reviewId}
    FAILED_TO_FIND_REVIEW(false,3030,"해당 리뷰를 찾을 수 없습니다."),
    //[DELETE] /movies/{movieIdx}
    FAILED_TO_FIND_MOVIE(false,3031,"해당 영화를 찾을 수 없습니다."),
    //[DELETE] /movies/keywords/{keywordIdx}
    FAILED_TO_FIND_KEYWORD(false,3032,"해당 상황 키워드를 찾을 수 없습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    DUPLICATE_KEY_ERROR(false, 4002, "이미 존재하는 row입니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    //[PATCH] /reviews/{reviewIdx}
    MODIFY_FAIL_REVIEW(false,4020,"리뷰 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    //[DELETE] /reviews/{reviewIdx}
    DELETE_FAIL_REVIEW(false,4030,"리뷰 삭제에 실패하였습니다."),
    //[DELETE] /movies/{movieIdx}
    DELETE_FAIL_MOVIE(false,4031,"영화 삭제에 실패하였습니다."),
    //[DELETE] /movies/keywords/{keywordIdx}
    DELETE_FAIL_KEYWORD(false,4032,"상황 키워드 삭제에 실패하였습니다."),

    //[POST] /reviews/{reviewIdx}/like
    CREATE_FAIL_REVIEWLIKE(false, 4040,"리뷰 좋아요에 실패하였습니다."),
    DELETE_FAIL_REVIEWLIKE(false,4041,"리뷰 좋아요 취소에 실패하였습니다."),

    /**
     * 5000 : File 오류
     */
    FILE_CONVERT_ERROR(false, 5000, "file 변환에 실패하였습니다."),
    FILE_DELETE_ERROR(false, 5001, "file 삭제에 실패했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
