package io.github.kimmking.gateway.config;

import io.github.kimmking.gateway.NettyServerApplication;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class ServerConfiguration {

    private final int port;
    private final List<ProxyProperties> proxyList;
    private static final ServerConfiguration INSTANCE = new ServerConfiguration();

    private ServerConfiguration() {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        ServerProperties properties = yaml.loadAs(NettyServerApplication.class.getResourceAsStream("/server.yml"), ServerProperties.class);//如果读入Map,这里可以是Mapj接口,默认实现为LinkedHashMap
        this.port = properties.getPort();
        this.proxyList = properties.getProxy().stream().peek(proxy -> proxy.setHost(formatUrl(proxy.getHost()))).collect(Collectors.toList());
    }

    public static ServerConfiguration getInstance() {
        return INSTANCE;
    }

    public int getPort() {
        return port;
    }

    public List<ProxyProperties> getProxyList() {
        return proxyList;
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/") ? backend.substring(0, backend.length() - 1) : backend;
    }
}
