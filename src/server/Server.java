package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.*;

import Common.src.Configuration;

public class Server {
	public static ServerSocket server = null;

	public static void main(String[] args) throws JSONException, IOException {
		initServerSocekt();
		waitForConnection();
	}

	public static void initServerSocekt() throws IOException {
		server = new ServerSocket(Configuration.PORT);
	}

	public static void waitForConnection() throws IOException {
		System.out.println("Waiting for connection...");
		while(true) {
			Socket client = server.accept();
			new Thread(new SocketThread(client)).start();
		}
	}
}
