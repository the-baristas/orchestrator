package com.utopia.orchestrator;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.netflix.appinfo.AmazonInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OrchestratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrchestratorApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    // @Bean
    // @Profile("!default")
    // public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils)
    // {
    // EurekaInstanceConfigBean bean = new EurekaInstanceConfigBean(inetUtils);
    // AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
    // bean.setDataCenterInfo(info);
    // return bean;
    // }

    // @Bean
    // @Profile("!default")
    // public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils)
    // {
    // EurekaInstanceConfigBean bean = new EurekaInstanceConfigBean(inetUtils);
    // AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
    // bean.setDataCenterInfo(info);

    // try {
    // String json = readEcsMetadata();
    // EcsTaskMetadata metadata = Converter.fromJsonString(json);
    // String ipAddress = findContainerPrivateIP(metadata);
    // bean.setIpAddress(ipAddress);
    // bean.setNonSecurePort(getPortNumber());
    // } catch (Exception ex) {
    // e.printStackTrace();
    // }
    // return bean;
    // }

    @Bean
    @Profile("!default")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        if (inetUtils != null) {
            EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(
                    inetUtils);
            String ip = null;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            config.setIpAddress(ip);
            config.setNonSecurePort(getPort());
            config.setPreferIpAddress(true);
            return config;
        }
        return null;
    }

    public Integer getPort() {
        return 0;
    }
}
