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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.lzz.netty.spring.entity.User;
import io.lzz.netty.spring.repository.UserRepository;

/**
 * @author q1219331697
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NettySpringApplicationTest {

	private static final Logger log = LoggerFactory.getLogger(NettySpringApplicationTest.class);

	@Autowired
	private UserRepository userRepository;

	@Test
	public void init() {
		for (int i = 0; i < 10; i++) {
			userRepository.save(new User(null, "username" + i, "pwd" + i));
		}
	}

	@Test
	public void testMain1() throws Exception {

		long begin = System.currentTimeMillis();

		ExecutorService threadPool = Executors.newCachedThreadPool();
		// ExecutorService threadPool = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 20; i++) {

			threadPool.submit(new Runnable() {
				public void run() {
					try {
						testMain();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			log.info("{}", i);
		}
		threadPool.shutdown();

		// 死循环阻塞主线程退出
		int i = 1;
		while (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
			log.info("wait i={}", i++);
		}

		log.info("{}", System.currentTimeMillis() - begin);
	}

	@Test
	public void testMain2() throws Exception {

		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						testMain();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
			thread.join();

			log.info("{}", i);
		}
	}

	@Test
	public void testMain() throws Exception {
		Socket socket = new Socket();
		String hostname = "localhost";
		int port = 8080;
		InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname, port);
		socket.connect(inetSocketAddress, 3000);
		// boolean connected = socket.isConnected();

		InputStream inputStream = socket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);

		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

		String ping = "ping";
		dataOutputStream.writeInt(ping.length());
		dataOutputStream.write(ping.getBytes());
		dataOutputStream.flush();

		int readInt = dataInputStream.readInt();
		byte[] bs = new byte[readInt];
		dataInputStream.readFully(bs);
		String string = new String(bs);
		// System.out.println(string);
		log.info(string);

		dataInputStream.close();
		inputStream.close();
		dataOutputStream.close();
		outputStream.close();
		socket.close();

		// System.out.println("end");
	}

}
