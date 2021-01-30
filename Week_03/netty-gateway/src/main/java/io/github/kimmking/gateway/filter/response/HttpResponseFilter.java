package io.github.kimmking.gateway.filter.response;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {

    void filter(FullHttpResponse response);

    default int order() {
        return 0;
    }

}
