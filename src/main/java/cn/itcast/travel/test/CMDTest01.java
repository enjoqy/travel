package cn.itcast.travel.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

/**
 * @author junhi
 * @date 2019/6/16 10:57
 */
public class CMDTest01 {

    public static void main(String[] args) throws IOException {
//        URL dir = CMDTest01.class.getResource("/");
//        System.out.println(dir);
        Runtime.getRuntime().exec(CMDTest01.class.getClassLoader().getResource("redis_server.bat").getPath());
    }

    @Test
    public void test01() throws IOException {
//        URL dir = CMDTest01.class.getResource("/");
//        System.out.println(dir);
//        Runtime.getRuntime().exec(dir + "redis_server.bat");

        Runtime.getRuntime().exec(this.getClass().getClassLoader().getResource("redis_server.bat").getPath());
    }
}
