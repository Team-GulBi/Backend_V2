package com.gulbi.Backend.global.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSender {

    @Value("${fastapi.host}")
    private String host;

    @Value("${fastapi.port}")
    private int port;

    public String sendFile(MultipartFile file) throws IOException {
        try (Socket socket = new Socket(host, port)) {  // 서버에 연결
            InputStream fileInputStream = file.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            // 파일을 소켓을 통해 전송
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();  // 데이터 전송 완료 후 버퍼 비우기

            // 전송 완료 후 소켓 종료
            socket.shutdownOutput();

            // 소켓 서버로부터 응답 받기
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
