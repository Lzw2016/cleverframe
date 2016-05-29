package example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 模拟服务端
 *
 * 作者：LiZW <br/>
 * 创建时间：2016-5-28 23:55 <br/>
 */
public class MockServerClientTest {
    private final static Logger logger = LoggerFactory.getLogger(MockServerClientTest.class);

    RestTemplate restTemplate;

    MockRestServiceServer mockRestServiceServer;

    String baseUri = "http://localhost:8080/users";

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        //模拟一个服务器
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testFindById() throws Throwable {
        String uri = baseUri + "/{id}";
        Long id = 1L;
        String resultJson = "{\"param1\":\"value1\",\"param2\":\"value2\"}";
        String requestUri = UriComponentsBuilder.fromUriString(uri).buildAndExpand(id).toUriString();

        //添加服务器端断言
        mockRestServiceServer
                .expect(MockRestRequestMatchers.requestTo(requestUri))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(resultJson, MediaType.APPLICATION_JSON));

        //2、访问URI（与API交互）
        ResponseEntity<String> entity = restTemplate.getForEntity(uri,String.class, id);

        //3.1、客户端验证
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        logger.info(entity.getHeaders().getContentType().toString());
        logger.info(entity.getBody());


//        //3.2、服务器端验证（验证之前添加的服务器端断言）
        mockRestServiceServer.verify();
    }
}
