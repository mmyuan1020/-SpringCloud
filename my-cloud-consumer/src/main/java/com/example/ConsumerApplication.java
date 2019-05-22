package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.controller.ConsumerController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@RibbonClient(name="ribbonClient",configuration=ConsumerController.MyBalancerConfig.class)
public class ConsumerApplication {

	public static void main(String[] args) {org.springframework.boot.actuate.endpoint.web.ServletEndpointRegistrar tt;
	org.springframework.beans.factory.support.DefaultListableBeanFactory t2;
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
