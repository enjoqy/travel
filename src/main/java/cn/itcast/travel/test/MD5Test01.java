package cn.itcast.travel.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

/**
 * @author junhi
 * @date 2019/6/17 16:17
 */
public class MD5Test01 {

    @Test
    public void test01(){
        String code = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(code);
    }

    @Test
    public void test02(){
        String code = DigestUtils.md5DigestAsHex("123456".getBytes());
        String tmp = DigestUtils.md5DigestAsHex(code.substring(0,8).getBytes());
        System.out.println(tmp);
    }
}
