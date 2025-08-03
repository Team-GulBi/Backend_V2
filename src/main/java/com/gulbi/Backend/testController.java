package com.gulbi.Backend;

// import com.gulbi.Backend.global.util.FileSender;
import com.gulbi.Backend.global.util.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class testController {
    // private final FileSender fileSender;
    private final S3Uploader s3Uploader;


    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String response = s3Uploader.uploadFile(file, "images");
        return response;
    }
}
