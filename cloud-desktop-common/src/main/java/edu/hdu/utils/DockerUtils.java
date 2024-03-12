package edu.hdu.utils;

import com.github.dockerjava.api.DockerClient;
import edu.hdu.entity.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DockerUtils {

    @Autowired
    private DockerClient dockerClient;

    public void removeContainers(List<Container> containers){
        for (Container container : containers) {
            dockerClient.stopContainerCmd(container.getContainerId()).exec();
            dockerClient.removeContainerCmd(container.getContainerId()).exec();
        }
    }
}
