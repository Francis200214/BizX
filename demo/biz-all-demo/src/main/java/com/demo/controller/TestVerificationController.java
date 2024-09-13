package com.demo.controller;

import com.biz.verification.annotation.BizXEnableCheck;
import com.demo.controller.vo.TestVerificationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试参数校验接口
 *
 * @author francis
 * @since 1.0.1
 **/
@Slf4j
@RestController
@RequestMapping("/verification")
public class TestVerificationController {


    @BizXEnableCheck
    @PostMapping("/test")
    public void test(@RequestBody TestVerificationVo testVerificationVo) {
        log.info("testVerificationVo:{}", testVerificationVo);
    }


}
