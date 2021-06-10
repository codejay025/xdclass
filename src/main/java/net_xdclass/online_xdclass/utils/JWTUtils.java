package net_xdclass.online_xdclass.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net_xdclass.online_xdclass.model.entity.User;

import java.util.Date;

/**
 * JWTUtils工具类
 */
public class JWTUtils {

    /**
     * 设置过期时间
     */
    private static final long EXPIRE = 60000 * 60 * 24 * 7;

    /**
     * 设置加密密钥
     */
    private final static String SECRET = "xdclass.net168";

    /**
     * 设置令牌主题SUBJECT
     */
    private final static String SUBJECT = "xdclass";

    /**
     * 设置令牌前缀
     */
    private final static String TOKEN_PREFIX = "xdclass";


    /**
     * 根据用户信息，生成令牌
     * @param user
     * @return
     *
     * Jwts:JWT工具类
     * setSubject():设置JWT的主题
     * claim():设置Jwt中的PayLoad，描述加密对象的信息，以键值对的形式
     * setIssuedAt():设置token创建时间
     * setExpiration():设置过期时间
     * signWith():第一个参数是加密算法，第二个参数是
     */
    public  static String geneJsonWebToken(User user){
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;

        return token;
    }

    /**
     * 验证token
     */
    public static Claims checkJWT(String token){
        try {
            /**
             * 验证Token，解析密钥，获取密钥信息
             * */
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
