package example;

import org.cleverframe.core.controller.ConfigController;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * 单独测试Controller示例<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-28 19:55 <br/>
 */
public class ControllerTest {
    private final static Logger logger = LoggerFactory.getLogger(ControllerTest.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        ConfigController configController = new ConfigController();
        mockMvc = MockMvcBuilders.standaloneSetup(configController).addPlaceholderValue("mvcPath", "mvc").build();
    }

    @Test
    public void example() throws Exception {
        // 发送请求到服务端
        String paramJson = "{\"param1\":\"value1\",\"param2\":\"value3\"}";
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/mvc/core/config/test.json")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("paramJson", paramJson));
        // 处理响应数据
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        logger.info("### 发送请求到服务端，响应数据-" + result);
    }
}
