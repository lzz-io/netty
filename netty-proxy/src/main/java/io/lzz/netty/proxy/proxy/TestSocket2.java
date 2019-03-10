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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author q1219331697
 *
 */
public class TestSocket2 {

	public static void main(String[] args) throws Exception {

		// Socket socket = new Socket("127.0.0.1", 11111);
		Socket socket = new Socket();
		InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 11111);
		socket.connect(inetSocketAddress, 3000);

		// 消息发送给服务器
		// 获取输出流，向服务器端发送信息
		OutputStream outputStream = socket.getOutputStream();// 字节输出流
		PrintWriter pw = new PrintWriter(outputStream); // 将输出流包装为打印流
//		pw.write("ping");
		pw.println("ping");
		pw.flush();
//		socket.shutdownOutput();

		// // 获取服务器返回消息
		// // 获取输入流，读取服务器端的响应
		InputStream inputStream = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String info = null;

		info = br.readLine();
		System.out.println(info);
		// while ((info = br.readLine()) != null) {
		// System.out.println("我是客户端，服务器说：" + info);
		// // 发送给client
		// }
//		socket.shutdownInput();

		// 关闭资源
		// socket.shutdownInput();
		// socket.shutdownOutput();
		br.close();
		inputStream.close();
		pw.close();
		outputStream.close();
		socket.close();

		System.out.println("end");
	}

}
