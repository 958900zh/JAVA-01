package io.github.kimmking.gateway.config;

import lombok.Data;

import java.util.List;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

@Data
public class ServerProperties {

    private int port;

    private List<ProxyProperties> proxy;

}
