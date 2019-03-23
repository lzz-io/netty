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

package io.lzz.netty.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.lzz.netty.spring.server.NettySpringServer;

/**
 * @author q1219331697
 */
@SpringBootApplication
public class NettySpringApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(NettySpringApplication.class);

	@Autowired
	private NettySpringServer nettySpringServer;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(NettySpringApplication.class, args);
		log.info("app started");
	}

	@Override
	public void run(String... args) throws Exception {
		new Thread(new Runnable() {

			@Override
			public void run() {
				nettySpringServer.run();
			}
		}, "netty-server").start();

	}

}
