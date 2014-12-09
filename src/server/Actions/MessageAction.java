package server.Actions;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import server.Server;
import server.ServerComfy;
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
			User targetUser = Server.users.get(data.get("to"));
			if (targetUser == null) sendTargetUserOffline();
			else targetUser.comfy.send("message", data);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void sendTargetUserOffline() throws JSONException {
		JSONObject data = new JSONObject();
		data.put("from", ":server");
		data.put("to", ":you");
		data.put("info", "chat is not sent.");
		data.put("content", ":the user you want to chat with is offline.");
		comfy.send("message", data);
	}
}
