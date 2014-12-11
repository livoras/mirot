package server.Actions;

import java.util.ArrayList;

import org.json.JSONException;

import server.Sender;
import server.Server;
import server.ServerComfy;
import Common.src.Action;
import Common.src.User;

public class CreateGroupAction extends Action {

	private ServerComfy comfy;

	public CreateGroupAction(ServerComfy comfy) {
		this.comfy = comfy;
	}

	@Override
	public void run() {
		super.run();
		try {
			createNewRoom();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<User> createNewRoom() throws JSONException {
		String username = data.getString("creator");
		String roomName = data.getString("room name");
		User creator = Server.users.get(username);
		ArrayList<User> usersInNewRoom = new ArrayList<User>();
		usersInNewRoom.add(creator);
		Server.rooms.put(roomName, usersInNewRoom);
		Sender.sendRoomsList();
		Sender.enterRoomSuccessed(comfy, roomName, usersInNewRoom);
		return usersInNewRoom;
	}

}
