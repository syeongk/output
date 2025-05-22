package com.sw.output.domain.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.global.response.ApiResponse;

@RestController
public class TestController {

    @GetMapping("/api/test/success")
    public ApiResponse<String> testSuccess() {
        return ApiResponse.success();
    }

}
