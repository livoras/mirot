package server.Actions;

import org.json.JSONException;
import org.json.JSONObject;

import server.Sender;
import server.Server;
import server.ServerComfy;
import Common.src.Action;
import Common.src.Logger;
import Common.src.User;

public class LoginAction extends Action {
	private ServerComfy comfy = null;

	public LoginAction(ServerComfy comfy) {
		this.comfy = comfy;
	}

	@Override
	public void run() {
		super.run();
		Logger.log(data);
		try {
			if (Server.users.get(data.get("name")) == null) {
				createNewUser();
			} else {
				refuseLogin();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void refuseLogin() throws JSONException {
		JSONObject data = new JSONObject();
		data.put("info", "Username has exited");
		comfy.send("refuse login", data);
	}

	private void createNewUser() throws JSONException {
		String name = data.getString("name");
		User newUser = new User(name, comfy);
		comfy.name = name;
		Server.users.put(name, newUser);
		Sender.sendUsersList();
		Sender.sendRoomsList();
		Logger.log(name + " login, current totoal online users count " + Server.users.size());
		callback();
	}

	private void callback() throws JSONException {
		JSONObject data = new JSONObject();
		data.put("info", "Login successed!");
		comfy.send("login successed", data);
	}
}
