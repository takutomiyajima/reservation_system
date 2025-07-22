package app.status;

import java.util.List;

import app.AppException;

public class CheckRoomStatusForm {
    private CheckRoomStatusControl control = new CheckRoomStatusControl();

    private CheckRoomStatusControl getControl() {
        return control;
    }

    public List getStatuses() throws AppException {
        CheckRoomStatusControl c = getControl();
        return c.checkStatus();
    }
}
