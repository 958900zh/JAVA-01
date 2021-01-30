package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.config.ProxyProperties;
import io.github.kimmking.gateway.config.ServerConfiguration;

import java.util.List;

public abstract class HttpEndpointRouter {

    private final List<ProxyProperties> proxyProperties;

    public HttpEndpointRouter() {
        this.proxyProperties = ServerConfiguration.getInstance().getProxyList();
    }

    public abstract String route();

    protected List<ProxyProperties> getProxyProperties() {
        return proxyProperties;
    }

}
