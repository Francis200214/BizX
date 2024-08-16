package com.demo.controller.vo;

import com.biz.verification.annotation.check.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 测试参数校验对象
 *
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
public class TestVerificationClintVo {

    @BizXCheckIsNull(isNull = false)
    private String strClintIsNull;

    @BizXCheckSize(min = 1, max = 3)
    private String strClintSize;



}
