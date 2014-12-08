package server;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONException;
import Common.src.Action;
import Common.src.Comfy;


public class SocketThread implements Runnable {
	private Socket socket = null;
	private Comfy comfy = null;

	public SocketThread(Socket socket) throws IOException {
		this.socket = socket;
		this.comfy = new Comfy(socket);
        System.out.println("Someone connect you!");
	}

	@Override
	public void run() {
		comfy.accept("message", new Action() {
			@Override
			public void run() {
				super.run();
				System.out.println("Data From Client " + socket.getRemoteSocketAddress() + " :" + data);
                try {
                	data.put("server says", "fuckyou");
					comfy.send("message", data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
