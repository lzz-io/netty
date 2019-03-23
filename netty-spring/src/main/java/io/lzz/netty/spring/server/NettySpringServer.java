/*
 * Copyright qq:1219331697
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lzz.netty.spring.server;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author q1219331697
 *
 */
@Component
public class NettySpringServer {

	private static final Logger log = LoggerFactory.getLogger(NettySpringServer.class);

	@Value("${server.port:8080}")
	private int port;

	@Autowired
	private NettySpringServerInitializer nettySpringServerInitializer;

	@PostConstruct
	public void startServer() {
		// 使用线程，不阻塞主线程
		new Thread(() -> run(), "netty-server").start();
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)//
					.channel(NioServerSocketChannel.class)//
					// .handler(new LoggingHandler(LogLevel.INFO))//
					.childHandler(nettySpringServerInitializer);

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync();
			log.info("server started at port {}", port);

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
