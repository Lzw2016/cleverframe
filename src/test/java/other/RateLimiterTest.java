package other;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/18 15:00 <br/>
 */
public class RateLimiterTest {

    @Test
    public void test01() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(2048);
        Thread.sleep(1000 * 3);
        System.out.println(limiter.acquire(2048));
        System.out.println(limiter.acquire(2048));
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
    }
}
