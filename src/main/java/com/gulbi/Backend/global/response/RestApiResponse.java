package com.gulbi.Backend.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestApiResponse {
    private HttpStatus status;
    private String code;
    private String message;
    private Object data;

    public RestApiResponse(ResponseApiCode responseApiCode, Object data){
        this.status = responseApiCode.getStatus();
        this.code = responseApiCode.getCode();
        this.message=responseApiCode.getMessage();
        this.data = data;
    }
    public RestApiResponse(ResponseApiCode responseApiCode){ //response data가 필요없는경우
        this.status = responseApiCode.getStatus();
        this.code = responseApiCode.getCode();
        this.message=responseApiCode.getMessage();
    }



}
