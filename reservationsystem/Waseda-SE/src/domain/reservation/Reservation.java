/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.reservation;

import java.util.Date;

/**
 * Reservation entity<br>
 * 
 */
public class Reservation {

    public static final String RESERVATION_STATUS_CREATE = "create";

    public static final String RESERVATION_STATUS_CONSUME = "consume";

    public static final String RESERVATION_STATUS_CANCEL = "cancel";

	private String reservationNumber;

        private Date stayingDate;

        private String status;

        private String roomType;

	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public Date getStayingDate() {
		return stayingDate;
	}

	public void setStayingDate(Date stayingDate) {
		this.stayingDate = stayingDate;
	}

	public String getStatus() {
		return status;
	}

        public void setStatus(String status) {
                this.status = status;
        }

        public String getRoomType() {
                return roomType;
        }

        public void setRoomType(String roomType) {
                this.roomType = roomType;
        }
}
