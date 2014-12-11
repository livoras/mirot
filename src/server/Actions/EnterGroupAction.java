package server.Actions;

import java.util.ArrayList;
import org.json.JSONException;
import server.Sender;
import server.Server;
import Common.src.Action;
import Common.src.Comfy;
import Common.src.User;

public class EnterGroupAction extends Action {
	/**
	 * Data should have format below:
	 * 	"room name": String - the room name user want to enter.
	 * 	"username": String - the name of whom want to enter the room.
	 * **/
	public Comfy comfy = null;
	
	public EnterGroupAction(Comfy comfy) {
		this.comfy = comfy;
	}

	@Override
	public void run() {
		super.run();
		String roomName;
		try {
			roomName = data.getString("room name");
			ArrayList<User> usersInRoom = Server.rooms.get(roomName);
			if (usersInRoom != null) {
				addUserToRoom(usersInRoom);
				Sender.enterRoomSuccessed(comfy, roomName, usersInRoom);
			} else {
				Sender.enterRoomFailed(comfy, usersInRoom);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void addUserToRoom(ArrayList<User> usersInRoom) throws JSONException {
		User toAddUser = Server.users.get(data.getString("username"));
		usersInRoom.add(toAddUser);
	}

}
