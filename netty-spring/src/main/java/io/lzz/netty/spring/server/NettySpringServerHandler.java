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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author q1219331697
 *
 */
@Component
public class NettySpringServerHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger log = LoggerFactory.getLogger(NettySpringServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		log.info("msg={},ctx={}", msg, ctx);
		ChannelFuture channelFuture = ctx.writeAndFlush("server pong!");
		channelFuture.addListener(ChannelFutureListener.CLOSE);
		log.info("server pong");

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("ctx={}", ctx, cause);
		ctx.close(); // 关闭发生异常的连接
	}

}
