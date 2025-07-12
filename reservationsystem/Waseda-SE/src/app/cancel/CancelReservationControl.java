package app.cancel;

import java.util.Date;

import app.AppException;
import app.ManagerFactory;
import domain.reservation.ReservationManager;
import domain.reservation.ReservationException;
import domain.reservation.Reservation;
import domain.room.RoomManager;
import domain.room.RoomException;

public class CancelReservationControl {
    public void cancel(String reservationNumber) throws AppException {
        try {
            ReservationManager reservationManager = getReservationManager();
            Reservation reservation = reservationManager.cancelReservation(reservationNumber);
            Date stayingDate = reservation.getStayingDate();
            RoomManager roomManager = getRoomManager();
            roomManager.updateRoomAvailableQty(stayingDate, 1);
        } catch (ReservationException e) {
            AppException exception = new AppException("Failed to cancel reservation", e);
            exception.getDetailMessages().add(e.getMessage());
            exception.getDetailMessages().addAll(e.getDetailMessages());
            throw exception;
        } catch (RoomException e) {
            AppException exception = new AppException("Failed to cancel reservation", e);
            exception.getDetailMessages().add(e.getMessage());
            exception.getDetailMessages().addAll(e.getDetailMessages());
            throw exception;
        }
    }

    private ReservationManager getReservationManager() {
        return ManagerFactory.getInstance().getReservationManager();
    }

    private RoomManager getRoomManager() {
        return ManagerFactory.getInstance().getRoomManager();
    }
}
