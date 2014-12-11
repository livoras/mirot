package server;

import java.io.IOException;
import java.net.Socket;

import server.Actions.CreateGroupAction;
import server.Actions.EnterGroupAction;
import server.Actions.LoginAction;
import server.Actions.MessageAction;
import Common.src.Logger;

public class SocketThread implements Runnable {
	private ServerComfy comfy = null;

	public SocketThread(Socket socket) throws IOException {
		this.comfy = new ServerComfy(socket);
		Logger.log("Someone connect you!");
	}

	@Override
	public void run() {
		comfy.accept("login", new LoginAction(comfy));
		comfy.accept("message", new MessageAction(comfy));
		comfy.accept("enter a group", new EnterGroupAction(comfy));
		comfy.accept("create a group", new CreateGroupAction(comfy));
	}
}
