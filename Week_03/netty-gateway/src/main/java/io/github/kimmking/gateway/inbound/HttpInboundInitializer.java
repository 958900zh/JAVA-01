package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.router.Activate;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.reflections.Reflections;

import java.util.Set;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new HttpInboundHandler(initRouter()));
	}

	private HttpEndpointRouter initRouter() {
		Reflections reflections = new Reflections(HttpEndpointRouter.class.getPackage().getName());
		Set<Class<? extends HttpEndpointRouter>> classes = reflections.getSubTypesOf(HttpEndpointRouter.class);

		for (Class<? extends HttpEndpointRouter> clazz : classes) {
			if (clazz.isAnnotationPresent(Activate.class)) {
				try {
					return clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
			return new RandomHttpEndpointRouter();
		}
}
