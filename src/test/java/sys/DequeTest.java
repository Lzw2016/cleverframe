package sys;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/15 18:35 <br/>
 */
public class DequeTest {

    @Test
    public void test(){
        Deque<Serializable> loginSessionDeque =new ArrayDeque<>();
        loginSessionDeque.push("1");
        loginSessionDeque.push("2");
        loginSessionDeque.push("3");
        loginSessionDeque.push("4");

        Serializable last = loginSessionDeque.removeLast();
        Serializable first = loginSessionDeque.removeFirst();

        System.out.println(last);
        System.out.println(first);
    }
}
