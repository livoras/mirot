package server;

import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import Common.src.Logger;
import Common.src.User;

public class Sender {
	public static void sendUsersList() throws JSONException {
		Set<String> keySet = Server.users.keySet();
		JSONObject data = new JSONObject();
		data.put("users", keySet);
		for (Entry<String, User> entry: Server.users.entrySet()) {
			User user = entry.getValue();
			user.comfy.send("users list", data);
		}
	}
}
