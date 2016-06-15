package basetest;

import org.cleverframe.core.controller.ConfigController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 *
 *
 * 集成Web环境测试的测试基类，适用如下场景：<br/>
 * Controller测试，调用请求，并获取到响应的数据<br/>
 * <b>注意：如果Controller返回到一个Jsp页面，就获取不到返回的页面数据，只能获取到ForwardedUrl</b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-26 9:15 <br/>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:/spring/spring-context-all.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:/spring/spring-mvc-all.xml")
})
public class TestByServletContextBase extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ServletContext servletContext;

    protected MockMvc mockMvc;

    @Autowired
    private ConfigController configController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        try {
            logger.info("#### Web容器根路径-" + webApplicationContext.getServletContext().getResource("").getFile());
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void example() throws Exception {
        // 发送请求获取JSP页面
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/mvc/core/config/index"));
        // 处理响应数据
        MvcResult mvcResult = resultActions.andReturn();
        String forwardedUrl = mvcResult.getResponse().getForwardedUrl();
        logger.info("### 请求JSP页面地址，跳转地址-" + forwardedUrl);

        // 发送请求到服务端
        String paramJson = "{\"param1\":\"value1\",\"param2\":\"value3\"}";
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/mvc/core/config/test.json")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("paramJson", paramJson));
        // 处理响应数据
        mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        logger.info("### 发送请求到服务端，响应数据-" + result);
    }
}
