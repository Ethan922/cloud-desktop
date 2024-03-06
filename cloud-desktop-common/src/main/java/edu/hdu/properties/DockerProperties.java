package edu.hdu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "docker")
@Data
public class DockerProperties {
    private String host;
    private String apiVersion;
    private String registryUrl;
    private int maxConnections;
}
