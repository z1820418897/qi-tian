package com.zhc;

import com.zhc.tcp.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@Slf4j
@SpringBootApplication
public class FlutterServerApplication implements CommandLineRunner {

    @Resource
    private NettyServer nettyServer;

    public static void main(String[] args) {
        log.info("启动springboot");
        SpringApplication.run(FlutterServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("启动netty");
        nettyServer.run();
    }
}
