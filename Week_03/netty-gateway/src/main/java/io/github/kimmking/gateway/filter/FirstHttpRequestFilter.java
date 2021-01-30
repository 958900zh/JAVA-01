package io.github.kimmking.gateway.filter;

import io.github.kimmking.gateway.inbound.HttpInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class FirstHttpRequestFilter implements HttpRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        System.out.println("I'm FirstHttpRequestFilter, I'm always the first to execute (*^▽^*)");
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}
