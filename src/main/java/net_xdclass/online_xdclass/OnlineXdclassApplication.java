package net_xdclass.online_xdclass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("net_xdclass.online_xdclass.mapper")
/* 添加事务 */
@EnableTransactionManagement
public class OnlineXdclassApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineXdclassApplication.class, args);
    }

}
