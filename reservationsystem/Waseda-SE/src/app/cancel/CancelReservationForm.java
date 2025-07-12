package app.cancel;

import app.AppException;
import app.cancel.CancelReservationControl;

public class CancelReservationForm {
    private CancelReservationControl cancelControl = new CancelReservationControl();
    private String reservationNumber;

    private CancelReservationControl getCancelReservationControl() {
        return cancelControl;
    }

    public void cancel() throws AppException {
        CancelReservationControl control = getCancelReservationControl();
        control.cancel(reservationNumber);
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
