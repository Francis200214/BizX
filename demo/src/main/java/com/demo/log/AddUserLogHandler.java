package com.demo.log;

import com.biz.web.log.handler.LogHandler;
import com.biz.web.log.handler.LogLargeTypeDefaultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 添加用户信息日志
 *
 * @author francis
 * @create 2024-06-03 13:51
 **/
@Service
@Slf4j
@LogLargeTypeDefaultHandler(LogTypeConstant.USER_LOG)
public class AddUserLogHandler implements LogHandler {


    @Override
    public void handleLog(String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e) {
        if (e == null) {
            log.info("logLargeType: {} logSmallType: {} operatorId: {} operatorName: {} content: {}", logLargeType, logSmallType, operatorId, operatorName, content);
        } else {
            log.info("logLargeType: {} logSmallType: {} operatorId: {} operatorName: {} content: {} e: ", logLargeType, logSmallType, operatorId, operatorName, content, e);
        }
    }


}
