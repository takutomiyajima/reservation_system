package app.setup;

import app.ManagerFactory;
import domain.room.Room;
import domain.room.RoomManager;
import domain.room.RoomType;

public class RoomSetup {
    public static void main(String[] args) throws Exception {
        RoomManager manager = ManagerFactory.getInstance().getRoomManager();
        String[] standardRooms = {"1001", "1002", "1003", "1004", "1005"};
        for (String r : standardRooms) {
            Room room = new Room();
            room.setRoomNumber(r);
            room.setType(RoomType.STANDARD);
            manager.createRoom(room);
        }
        String[] suiteRooms = {"2001", "2002"};
        for (String r : suiteRooms) {
            Room room = new Room();
            room.setRoomNumber(r);
            room.setType(RoomType.SUITE);
            manager.createRoom(room);
        }
    }
}
