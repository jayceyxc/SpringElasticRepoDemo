package com.linus.es.demo.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.http.HttpHeaders;

import java.time.Duration;

/**
 * @author: yuxuecheng
 * @className: SpringElasticConfig
 * @ProjectName: demo
 * @description: TODO
 * @date: Created in 2020/2/6 10:06
 * @version: 1.0
 */
@Configuration
public class SpringElasticConfig {

    @Value("${es.host}")
    public String host;

    @Value("${es.port}")
    public int port;

    @Value("${es.scheme}")
    public String scheme;

    /**
     * 单位ms
     */
    @Value("${es.socket_timeout}")
    public int socketTimeout;

    /**
     * 单位ms
     */
    @Value("${es.connect_timeout}")
    public int connectTimeout;

    /**
     * ElasticSearch用户名
     */
    @Value("${es.username}")
    public String userName;

    /**
     * ElasticSearch密码
     */
    @Value("${es.password}")
    public String password;

    private String makeHostAndPort(String host, int port) {
        return host + ":" + port;
    }

    @Bean
    RestHighLevelClient restHighLevelClient() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(makeHostAndPort(host, port))
                .withBasicAuth(userName, password)
                .withSocketTimeout(Duration.ofMillis(socketTimeout))
                .withConnectTimeout(Duration.ofMillis(connectTimeout))
                .withDefaultHeaders(httpHeaders)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
