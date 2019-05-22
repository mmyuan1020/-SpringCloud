package com.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.client.config.IClientConfig;
import com.netflix.config.ChainedDynamicProperty;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;

import feign.RequestLine;

@RestController
public class ConsumerController {
	/*
	@Autowired HelloRemote helloRemote;
	
	@RequestMapping("/hello")
	public String hello(@RequestParam String name) {
		org.springframework.http.converter.ByteArrayHttpMessageConverter t;
		org.springframework.http.converter.StringHttpMessageConverter t1;
		org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList t2;
		com.netflix.config. ChainedDynamicProperty t3;
		LoadBalancerAutoConfiguration t4; 
		RibbonClientConfiguration t5;
		ZoneAwareLoadBalancer t6;
		ChainedDynamicProperty t7;
	//	feign.SynchronousMethodHandler ttt;
		org.springframework.cloud.openfeign.support.SpringMvcContract ttt;
		System.out.println("consumer: ==========================================>  "+name);
		return helloRemote.hello(name.toUpperCase());
	}
	
	@RequestMapping("/getUserMap2")
	@ResponseBody
	public  Map<String,Object> getUserMap() {
		return helloRemote.getUserMap();
	}

	   @Autowired
	   private LoadBalancerClient loadBalancerClient;
	
	*/
	
	
	@Autowired ExamplePlatform examplePlatform;
	@RequestMapping("/say")
	public String say(@RequestParam  String name) {
		org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient tt;
		//ServiceInstance instance = this.loadBalancerClient.choose("o2oProjectServer");
		//System.out.println("111: " + instance.getServiceId() + ": " + instance.getHost() + ": " + instance.getPort());
		 return examplePlatform.say(name);
	}
	
	
	public static class  MyBalancerConfig {
		//@Autowired IClientConfig clientConfig;
		//@Bean
		public ILoadBalancer rinbbonBalancer() {
			LoadBalancerAutoConfiguration t1;
			SpringClientFactory t2;
			org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList t3;
			
			DynamicServerListLoadBalancer<Server> b = new ZoneAwareLoadBalancer<>();
			
			DiscoveryEnabledNIWSServerList t4;
			
			
			
			Server ser=new Server("o2oProjectServer");
			ser.setHost("localhost");
			
			ser.setPort(8088);
			//ser.setSchemea("http");
			//ser.setZone("defalut");
					
			b.addServer(ser);;
			return b;
		}
	}
}

/*
@FeignClient(name= "spring-cloud-producer")
interface HelloRemote {
    @RequestMapping("/hello")
    String hello(@RequestParam(value = "name") String name);
    
    @RequestMapping(value = "/getUserMap")
    Map<String,Object> getUserMap();
}

*/





@FeignClient(name= "o2oProjectServer")
interface ExamplePlatform {
    @RequestMapping(value = "/say")
    String say(@RequestParam String name);
}











