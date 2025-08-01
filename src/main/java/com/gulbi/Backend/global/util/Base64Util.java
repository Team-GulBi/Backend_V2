package com.gulbi.Backend.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class Base64Util {

    public static String MultipartFileToString(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        return strBytes;
    }

//    public MultipartFile StringToMultipartFile(){};
}


