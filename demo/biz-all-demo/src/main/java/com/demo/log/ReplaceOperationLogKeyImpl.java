package com.demo.log;

import com.biz.operation.log.replace.ReplaceOperationLogKey;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

/**
 * 自定义替换操作日志内容
 *
 * @author francis
 **/
@Named
public class ReplaceOperationLogKeyImpl implements ReplaceOperationLogKey {

    @Autowired
    private static final String USER_ID = "userId";

    @Override
    public String replace(String content) {
        content.replace("userId", "当前用户ID");
        content.replace("userName", "当前用户名称");
        return content;
    }

}
