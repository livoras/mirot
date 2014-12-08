package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.*;

import Common.src.Configuration;

public class Server {
	public static ServerSocket server = null;

	public static void main(String[] args) throws JSONException {
		try {
		    initServerSocekt();
		    waitForConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initServerSocekt() throws IOException {
		try {
			server = new ServerSocket(Configuration.PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void waitForConnection() {
		System.out.println("Waiting for connection...");
		while(true) {
			try {
				Socket client = server.accept();
				Thread socketThead = new Thread(new SocketThread(client));
				socketThead.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
