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
public class TestVerificationVo {

    @BizXCheckIsNull(isNull = false)
    private String strIsNull;

    @BizXCheckSize(min = 1, max = 3)
    private String strSize;

    @BizXCheckIsNull(isNull = false)
    private TestVerificationClintVo testVerificationClintVo;

//    @BizXCheckCollectionIsEmpty(isEmpty = false)
//    private List<String> collect;
//
//    @BizXCheckDateTimeFormat(format = "yyyy-MM-dd")
//    private String date;
//
//    @BizXCheckDoubleMax(max = 10.1)
//    private String doubleMax;
//
//    @BizXCheckDoubleMin(min = 10.1)
//    private String doubleMin;
//
//    @BizXCheckFloatMax(max = 10.1f)
//    private String floatMax;
//
//    @BizXCheckFloatMin(min = 10.1f)
//    private String floatMin;
//
//    @BizXCheckIntegerMax(max = 10)
//    private String intMax;
//
//    @BizXCheckIntegerMin(min = 10)
//    private String intMin;
//
//    @BizXCheckLongMax(max = 10)
//    private String longMax;
//
//    @BizXCheckLongMin(min = 10)
//    private String longMin;
//
//    @BizXCheckShortMax(max = 10)
//    private String shortMax;
//
//    @BizXCheckShortMin(min = 10)
//    private String shortMin;


}
