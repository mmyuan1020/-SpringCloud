package org.lsqt.components.springcloud;

import com.alibaba.fastjson.JSON;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.EurekaClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.servlet.Filter;
/**
 * An example service (that can be initialized in a variety of ways) that registers with eureka
 * and listens for REST calls on port 8001.
 */
@Singleton
public class ExampleServiceBase {

    private final ApplicationInfoManager applicationInfoManager;
    private final EurekaClient eurekaClient;
    private final DynamicPropertyFactory configInstance;

    private final Filter filter;
    
    @Inject
    public ExampleServiceBase(ApplicationInfoManager applicationInfoManager,
                              EurekaClient eurekaClient,
                              DynamicPropertyFactory configInstance,Filter filter) {
        this.applicationInfoManager = applicationInfoManager;
        this.eurekaClient = eurekaClient;
        this.configInstance = configInstance;
        this.filter = filter;
    }

    @PostConstruct
    public void start() {
        // A good practice is to register as STARTING and only change status to UP
        // after the service is ready to receive traffic
        System.out.println("Registering service to eureka with STARTING status");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

        System.out.println("Simulating service initialization by sleeping for 2 seconds...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }

        // Now we change our status to UP
        System.out.println("Done sleeping, now changing status to UP");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        waitForRegistrationWithEureka(eurekaClient);
        System.out.println("Service started and ready to process requests..");
        
/*
        try {
            int myServingPort = applicationInfoManager.getInfo().getPort();  // read from my registered info
            ServerSocket serverSocket = new ServerSocket(myServingPort);
            final Socket s = serverSocket.accept();
            System.out.println("Client got connected... processing request from the client");
            processRequest(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        System.out.println("模拟服务正在工作 ，处理" + 5 + " 秒...");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            // Nothing
        }
    }

    @PreDestroy
    public void stop() {
        if (eurekaClient != null) {
            System.out.println("Shutting down server. Demo over.");
            eurekaClient.shutdown();
        }
    }
    com.netflix.config.DynamicPropertyFactory tt;
    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        // my vip address to listen on
        String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "o2o").get();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
               
                System.out.println(JSON.toJSONString(nextServerInfo, true));
            } catch (Throwable e) {
                System.out.println("Waiting ... verifying service registration with eureka ...");
                
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void processRequest(final Socket s) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = rd.readLine();
            if (line != null) {
                System.out.println("Received a request from the example client: " + line);
            }
            String response = line;
            System.out.println("Sending the response to the client: " + response);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(response);

        } catch (Throwable e) {
            System.err.println("Error processing requests");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
