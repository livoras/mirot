package server;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Common.src.Comfy;
import Common.src.User;

public class Sender {
	public static void sendUsersList() throws JSONException {
		JSONObject data = new JSONObject();
		Set<String> keySet = Server.users.keySet();
		data.put("users", keySet);
		for (Entry<String, User> entry: Server.users.entrySet()) {
			User user = entry.getValue();
			user.comfy.send("users list", data);
		}
	}

	public static void sendRoomsList() throws JSONException {
		JSONObject data = new JSONObject();
		Set<String> roomsNames = Server.rooms.keySet();
		data.put("rooms", roomsNames);
		Set<Entry<String, User>> entries = Server.users.entrySet();
		for (Entry<String, User> entry: entries) {
			User user = entry.getValue();
			user.comfy.send("update room list", data);
		}
	}

	public static void enterRoomSuccessed(Comfy comfy, String roomName, ArrayList<User> usersInRoom) throws JSONException {
		JSONObject responseData = new JSONObject();
		JSONArray users = new JSONArray();
		for (User user: usersInRoom) users.put(user.name);
		responseData.put("info", "OK!");
		responseData.put("users", users);
		responseData.put("room name", roomName);
		comfy.send("enter a group successed", responseData);
	}

	public static void enterRoomFailed(Comfy comfy, ArrayList<User> usersInRoom) throws JSONException {
		JSONObject error = new JSONObject();
		error.put("info", "enter room failed");
		comfy.send("error", error);
	}

	public static void sendTargetUserOffline(Comfy comfy) throws JSONException {
		JSONObject data = new JSONObject();
		data.put("info", "The user you want to chat with is off line");
		data.put("type", "user offline");
		comfy.send("error", data);
	}
}
