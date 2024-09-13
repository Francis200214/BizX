package com.demo.test;

import com.biz.common.strategy.LazySelectableAbstractStrategyService;
import com.biz.common.strategy.SelectableStrategyService;
import com.demo.controller.LoginController;
import com.demo.strategy.PeopleEnum;
import com.demo.strategy.PeopleStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author francis
 * @since 1.0.1
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ActiveProfiles
public class DemoTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private TestRestTemplate restTemplate;

    private SelectableStrategyService<PeopleEnum, PeopleStrategyService> peopleStrategyService = new LazySelectableAbstractStrategyService<PeopleEnum, PeopleStrategyService>() {
    };

    @Test
    public void test1() {
        PeopleStrategyService slelect = peopleStrategyService.select(PeopleEnum.女人);
        System.out.println("slelect " + slelect);
        slelect.handle();
    }

    @Test
    public void test() {
        loginController.login("d111111");
//        BizInterceptorYml bean = BizXBeanUtils.getBean(BizInterceptorYml.class);
//        log.info("bean {}", bean);
    }

}
