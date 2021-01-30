package io.github.kimmking.gateway.config;

import io.github.kimmking.gateway.NettyServerApplication;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class ServerConfiguration {

    private final ServerProperties properties;

    public ServerConfiguration() {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        this.properties = yaml.loadAs(NettyServerApplication.class.getResourceAsStream("/server.yml"), ServerProperties.class);//如果读入Map,这里可以是Mapj接口,默认实现为LinkedHashMap
    }

    public ServerProperties getProperties() {
        return properties;
    }
}
