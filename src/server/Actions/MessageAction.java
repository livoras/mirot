package server.Actions;

import java.util.ArrayList;
import org.json.JSONException;
import server.Server;
import Common.src.Action;
import Common.src.Comfy;
import Common.src.Logger;
import Common.src.User;

public class MessageAction extends Action {
	private Comfy comfy = null;

	public MessageAction(Comfy comfy) {
		this.comfy = comfy;
	}

	@Override
	public void run() {
		super.run();
		Logger.log("Data From Client " + comfy.socket.getRemoteSocketAddress() + " :" + data);
		try {
			if (isP2PChat()) sendP2PChat();
			else sendGroupChat();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void sendGroupChat() throws JSONException {
		ArrayList<User> users = Server.rooms.get(data.getString("to"));
		for (User user: users) {
			user.comfy.send("message", data);
		}
	}

	private void sendP2PChat() throws JSONException {
		User targetUser = Server.users.get(data.getString("to"));
		targetUser.comfy.send("message", data);
	}

	private boolean isP2PChat() throws JSONException {
		if (data.getString("type").equals("p2p")) return true;
		else return false;
	}

}
