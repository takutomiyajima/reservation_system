package app.status;

import java.util.List;

import app.AppException;
import app.ManagerFactory;
import domain.room.RoomManager;
import domain.room.RoomException;

public class CheckRoomStatusControl {
    public List checkStatus() throws AppException {
        try {
            RoomManager roomManager = getRoomManager();
            return roomManager.getAllRooms();
        } catch (RoomException e) {
            AppException exception = new AppException("Failed to get room status", e);
            exception.getDetailMessages().add(e.getMessage());
            exception.getDetailMessages().addAll(e.getDetailMessages());
            throw exception;
        }
    }

    private RoomManager getRoomManager() {
        return ManagerFactory.getInstance().getRoomManager();
    }
}
