package server;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONException;

import Common.src.Comfy;

public class ServerComfy extends Comfy {
	public String name = null;

	public ServerComfy(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public void closeAll() throws IOException {
		super.closeAll();
		try {
			Server.leaveAllRooms(name);
			Server.removeUser(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
