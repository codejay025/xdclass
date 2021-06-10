package net_xdclass.online_xdclass;

import io.jsonwebtoken.Claims;
import net_xdclass.online_xdclass.model.entity.User;
import net_xdclass.online_xdclass.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineXdclassApplicationTests {

    @Test
    public void testJWT(){
        User user = new User();
        user.setId(66);
        user.setName("xiaoxiao");
        user.setHeadImg("png");

        String token = JWTUtils.geneJsonWebToken(user);
        System.out.println(token);
        Claims claims = JWTUtils.checkJWT(token);
        System.out.println(claims.get("name"));

    }

}
