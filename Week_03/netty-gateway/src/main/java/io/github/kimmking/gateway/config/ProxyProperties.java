package io.github.kimmking.gateway.config;

import lombok.Data;

@Data
public class ProxyProperties {
    private String host;
    private int weight;
}