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

package io.lzz.netty.proxy.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author q1219331697
 *
 */
public class ProxyProxyHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger log = LoggerFactory.getLogger(ProxyProxyHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		log.info("msg={},ctx={}", msg, ctx);
		// ChannelFuture channelFuture = ctx.writeAndFlush("pong!");
		// channelFuture.addListener(ChannelFutureListener.CLOSE);

		// 实现server的client
		// Bootstrap bootstrap = new Bootstrap();
		// bootstrap.group(ctx.channel().eventLoop()).channel(ctx.channel().getClass())
		// .handler(new ChannelInitializer<SocketChannel>() {
		// @Override
		// protected void initChannel(SocketChannel ch) throws Exception {
		// ChannelPipeline pipeline = ch.pipeline();
		//
		// pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0,
		// 4));
		// pipeline.addLast(new LengthFieldPrepender(4));
		// pipeline.addLast(new StringDecoder());
		// pipeline.addLast(new StringEncoder());
		// // pipeline.addLast(new ReadTimeoutHandler(6));// 超时
		//
		// pipeline.addLast(new ChannelInboundHandlerAdapter() {
		// @Override
		// public void channelRead(ChannelHandlerContext ctx, Object msg) throws
		// Exception {
		// ctx.channel().writeAndFlush(msg);
		// log.info("ctx={}", ctx);
		// log.info("msg={}", msg);
		// }
		// });
		// }
		// });
		//
		// ChannelFuture channelFuture = bootstrap.connect("localhost", 11111);
		// channelFuture.addListener(new ChannelFutureListener() {
		// @Override
		// public void operationComplete(ChannelFuture future) throws Exception {
		// if (future.isSuccess()) {
		// future.channel().writeAndFlush(msg);
		// log.info("future={}", future);
		// log.info("msg={}", msg);
		// } else {
		// ctx.channel().close();
		// }
		// }
		// });

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("ctx={}", ctx, cause);
		ctx.close(); // 关闭发生异常的连接
	}

}
