package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;


public class SocketThread implements Runnable {
	private Socket client = null;

	public SocketThread(Socket client) {
		this.client = client;
        System.out.println("Someone connect you!");
	}

	@Override
	public void run() {
        System.out.println("Thread Started! " + client);
		try {
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(true) {
				System.out.print("Waiting message from client: ");;
				String str = in.readLine();
				System.out.println(str);
                if (str == null) {
                	System.out.println("Revieve null, break");
                	break;
                }
				if (str.equals("fuck")) {
					break;
				} else {
					JSONObject data = new JSONObject(str);
					data.put("server", "fuck you");
					out.println(data);
				}
			}
			// 关闭输入流和socket
			out.close();
			client.close();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
