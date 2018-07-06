package com.company.project;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created with CodeGenerator
 * Description:
 *  service 单元测试可继承此类
 * @author  LErry.li
 * Date: 2018年7月6日
 * Time: 下午11:16:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MockMvc mockMvc ;

    // 注入WebApplicationContext
    @Autowired
    private WebApplicationContext wac;

    // 在测试开始前初始化工作
    @Before
    public void setup() {
        logger.info("--------------------------"+this.getClass().getName()+"单元测试开始--------------------------");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @After
    public void teardown() {
        logger.info("--------------------------"+this.getClass().getName()+"单元测试结束--------------------------");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

}
