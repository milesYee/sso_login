package com.miles.member.config.shiro;

import cn.hutool.core.util.ObjectUtil;
import com.miles.member.config.redis.RedisClient;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Administrator
 * @Description UserShiroCasRealm
 * @Date 2020/4/9 19:59
 */
public class MyShiroCasRealm extends CasRealm {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private RedisClient redisClient;

    /**
     * 权限授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String key = appName+"_"+(String)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = redisClient.getObject(key, SimpleAuthorizationInfo.class);
        if (ObjectUtil.isNotNull(info)) {
            return info;
        }
        info = new SimpleAuthorizationInfo();
        //表明当前登录者的角色(真实项目中这里会去查询DB，拿到用户的角色，存到redis里)
        info.addRole("admin");
        //表明当前登录者的角色(真实项目中这里会去查询DB，拿到该角色的资源权限，存到redis里)
        info.addStringPermission("admin:manage");
        info.addStringPermission("admin:query");
        redisClient.setObject(key, info, 120);
        return info;
    }

    /**
     * 登录认证
     * 登录认证走cas服务
     *
     * @param authenticationToken
     * @return
     */
    /*@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        *//*UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //通过token去查询DB，获取用户的密码，这里密码直接写死
        User user = new User();
        user.setUsername(token.getUsername());
        return new SimpleAuthenticationInfo(user, "26bfdfe8689183e9235b1f0beb7a6f46",
                ByteSource.Util.bytes(user.getUsername()), getName());*//*
    }*/

    /**
     * 密码(123456) + salt(maple)，得出存进数据库里的密码：26bfdfe8689183e9235b1f0beb7a6f46
     *
     * @param args
     */
    public static void main(String[] args) {
        String hashAlgorithName = "MD5";
        String password = "123456";
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes("maple");
        SimpleHash simpleHash = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        String s = simpleHash.toHex();
        System.out.println(s);
    }


}
