package com.biz.security.demo.test;

import com.biz.common.jwt.JwtCreateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 退出登录测试
 *
 * @author francis
 * @create 2024-11-04
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class LogoutTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    public void setUp() {
        token = JwtCreateHelper.builder()
                .secret("secret_secret_secret_secret")
                .data("userId", "111")
                .build()
                .createToken();
    }


    /**
     * 测试退出登录
     */
    @Test
    public void loginAuthTypeIsNull() throws Exception {
        mockMvc.perform(get("/api/logout")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", token)
                )
                .andExpect(status().isOk());
    }


}
