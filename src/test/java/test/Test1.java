package test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class Test1 {
    @Resource
    StringRedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("2","1");
        String s = redisTemplate.opsForValue().get("2");
        System.out.println(s);
    }


}
