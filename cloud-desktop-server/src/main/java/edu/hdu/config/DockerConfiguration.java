package edu.hdu.config;


import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import edu.hdu.properties.DockerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

@Configuration
public class DockerConfiguration {

    /**
     * 连接docker服务器
     *
     * @return
     */
    @ConditionalOnMissingBean
    @Bean("dockerClient")
    public DockerClient connectDocker(DockerProperties dockerProperties) {
        String certPath=this.getClass()
                .getClassLoader()
                .getResource("ca")
                .getPath()
                .substring(1)
                .replace('/', File.separator.charAt(0));
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(true)
                .withDockerCertPath(certPath)
                // dokcer宿主机的ip,端口号
                .withDockerHost(dockerProperties.getHost())
                // docker API版本号
                .withApiVersion(dockerProperties.getApiVersion())
                // 默认注册中心地址
                .withRegistryUrl(dockerProperties.getRegistryUrl()).build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(dockerProperties.getMaxConnections())//最大连接数
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);

        Info info = dockerClient.infoCmd().exec();
        String infoStr = JSONObject.toJSONString(info);
        System.out.println("docker的环境信息如下：=================");
        System.out.println(infoStr);
        return dockerClient;
    }

}
