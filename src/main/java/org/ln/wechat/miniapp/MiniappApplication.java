package org.ln.wechat.miniapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@MapperScan("org.ln.wechat.miniapp.mapper")
@EnableJms
public class MiniappApplication {

  public static void main(String[] args) {
    SpringApplication.run(MiniappApplication.class, args);
  }
}
