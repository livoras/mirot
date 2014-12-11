package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.*;

import Common.src.Configuration;
import Common.src.Logger;
import Common.src.User;

public class Server {
	public static ServerSocket server = null;
	public static Map<String, User> users = new HashMap<String, User>();
	public static Map<String, ArrayList<User>> rooms = new HashMap<String, ArrayList<User>>();

	public static void main(String[] args) throws JSONException, IOException {
		initServerSocekt();
		waitForConnection();
	}

	public static void initServerSocekt() throws IOException {
		server = new ServerSocket(Configuration.PORT);
	}

	public static void waitForConnection() throws IOException {
		Logger.log("Waiting for connection...");
		while(true) {
			Socket client = server.accept();
			new Thread(new SocketThread(client)).start();
		}
	}

	public static void removeUser(String name) throws JSONException {
		users.remove(name);
		Logger.log(name + " logout, current total online users count is " + users.size());
		Sender.sendUsersList();
	}

	public static void leaveAllRooms(String name) {
		User toRemoveUser = users.get(name);
		Set<Entry<String, ArrayList<User>>> allRooms = rooms.entrySet();
		for(Entry<String, ArrayList<User>> entry: allRooms) {
			ArrayList<User> users = entry.getValue();
			String roomName = entry.getKey();
			if (users.remove(toRemoveUser)) {
				Logger.log("Remove user " + toRemoveUser.name + " from a room: " + roomName);
			}
		}
	}
}
