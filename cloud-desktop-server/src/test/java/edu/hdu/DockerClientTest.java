package edu.hdu;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Binds;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PortBinding;
import edu.hdu.pool.PortPool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DockerClientTest {

    @Autowired
    private DockerClient dockerClient;

    @Autowired
    private PortPool portPool;

    @Test
    public void test1() {
        for (int i=0;i<2;i++) {
            Integer hostPort = portPool.acquirePort();
            CreateContainerResponse containerResponse = dockerClient.createContainerCmd("kylin-2203:1.1")
                    .withHostConfig(new HostConfig()
                            .withPortBindings(PortBinding.parse(hostPort + ":8444")))
                    .withTty(true)
                    .exec();
            dockerClient.startContainerCmd(containerResponse.getId()).exec();
        }
    }

}
