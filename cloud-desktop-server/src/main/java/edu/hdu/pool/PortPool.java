package edu.hdu.pool;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PortPool {
    private static final int MIN_PORT = 5000; // 定义可用端口范围的最小值
    private static final int MAX_PORT = 6000; // 定义可用端口范围的最大值

    private final Set<Integer> availablePorts = new HashSet<>();

    public PortPool() {
        for (int i = MIN_PORT; i <= MAX_PORT; ++i) {
            availablePorts.add(i);
        }
    }

    /**
     * 获取一个空闲端口
     * @return 空闲端口，若没有则返回null或抛出异常
     */
    public synchronized Integer acquirePort() {
        if (availablePorts.isEmpty()) {
            throw new IllegalStateException("No more free ports in the pool.");
        }
        Integer port = availablePorts.iterator().next();
        availablePorts.remove(port);
        return port;
    }

    /**
     * 释放端口到池中
     * @param port 要释放的端口
     */
    public synchronized void releasePort(Integer port) {
        if (port >= MIN_PORT && port <= MAX_PORT && !availablePorts.contains(port)) {
            availablePorts.add(port);
        } else {
            throw new IllegalArgumentException("Invalid port or port already released: " + port);
        }
    }
}
