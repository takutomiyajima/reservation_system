/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.util.Date;

/**
 * Room entity<br>
 * 
 */
public class Room {

        private String roomNumber;

        private Date stayingDate;

        private String type;

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getStayingDate() {
		return stayingDate;
	}

        public void setStayingDate(Date stayingDate) {
                this.stayingDate = stayingDate;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }
}
