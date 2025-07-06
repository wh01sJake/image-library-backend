package com.intelijake.imagelibrarybackend.controller;

import com.intelijake.imagelibrarybackend.common.BaseResponse;
import com.intelijake.imagelibrarybackend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: MainController
 * Description:
 * <p>
 * Datetime: 2025/7/5 19:39
 * Author: @Likun.Fang
 * Version: 1.0
 */

@RestController
@RequestMapping("/")
public class MainController {



    @GetMapping("/health")
    public BaseResponse<String> health(){

        return ResultUtils.success("ok");
    }
}
