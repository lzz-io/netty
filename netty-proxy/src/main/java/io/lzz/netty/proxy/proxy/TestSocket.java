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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author q1219331697
 *
 */
public class TestSocket {

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket();
		InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 11111);
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
		System.out.println(string);

		dataInputStream.close();
		inputStream.close();
		dataOutputStream.close();
		outputStream.close();
		socket.close();

		System.out.println("end");
	}

}
