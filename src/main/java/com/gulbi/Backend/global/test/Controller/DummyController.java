package com.gulbi.Backend.global.test.Controller;

import com.gulbi.Backend.global.test.CategoryInitService;
// import com.gulbi.Backend.global.test.ProductDataInitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
@AllArgsConstructor
public class DummyController {
    private final CategoryInitService categoryInitService;
    // private final ProductDataInitService productDataInitService;

    @GetMapping("/categories")
    public void initCategory() throws Exception {
        categoryInitService.run();
    }

    // @GetMapping("/products")
    // public void initProducts() throws Exception {
    //     productDataInitService.run();
    //
    // }
}
