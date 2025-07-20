/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import util.DateUtil;
import app.AppException;
import app.checkin.CheckInRoomForm;
import app.checkout.CheckOutRoomForm;
import app.reservation.ReserveRoomForm;
import app.cancel.CancelReservationForm;
import app.status.CheckRoomStatusForm;
import domain.room.RoomType;

/**
 * CUI class for Hotel Reservation Systems
 * 
 */
public class CUI {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private BufferedReader reader;

	CUI() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	private void execute() throws IOException {
		try {
			while (true) {
				int selectMenu;
				System.out.println("");
				System.out.println("Menu");
                                System.out.println("1: Reservation");
                                System.out.println("2: Check-in");
                                System.out.println("3: Check-out");
                                System.out.println("4: Cancel reservation");
                                System.out.println("5: Room status");
                                System.out.println("9: End");
				System.out.print("> ");

				try {
					String menu = reader.readLine();
					selectMenu = Integer.parseInt(menu);
				}
                                catch (NumberFormatException e) {
                                        selectMenu = 5;
                                }

				if (selectMenu == 9) {
					break;
				}

				switch (selectMenu) {
					case 1:
						reserveRoom();
						break;
					case 2:
						checkInRoom();
						break;
                                        case 3:
                                                checkOutRoom();
                                                break;
                                        case 4:
                                                cancelReservation();
                                                break;
                                        case 5:
                                                showRoomStatus();
                                                break;
                                }
			}
			System.out.println("Ended");
		}
		catch (AppException e) {
			System.err.println("Error");
			System.err.println(e.getFormattedDetailMessages(LINE_SEPARATOR));
		}
		finally {
			reader.close();
		}
	}

	private void reserveRoom() throws IOException, AppException {
		System.out.println("Input arrival date in the form of yyyy/mm/dd");
		System.out.print("> ");

		String dateStr = reader.readLine();

		// Validate input
		Date stayingDate = DateUtil.convertToDate(dateStr);
		if (stayingDate == null) {
			System.out.println("Invalid input");
			return;
		}

                System.out.println("Select room type: 1: Standard 2: Suite");
                System.out.print("> ");
                String typeStr = reader.readLine();
                String roomType = "1".equals(typeStr) ? RoomType.STANDARD : RoomType.SUITE;

                ReserveRoomForm reserveRoomForm = new ReserveRoomForm();
                reserveRoomForm.setStayingDate(stayingDate);
                reserveRoomForm.setRoomType(roomType);
                String reservationNumber = reserveRoomForm.submitReservation();

		System.out.println("Reservation has been completed.");
		System.out.println("Arrival (staying) date is " + DateUtil.convertToString(stayingDate) + ".");
		System.out.println("Reservation number is " + reservationNumber + ".");
	}

	private void checkInRoom() throws IOException, AppException {
		System.out.println("Input reservation number");
		System.out.print("> ");

		String reservationNumber = reader.readLine();

		if (reservationNumber == null || reservationNumber.length() == 0) {
			System.out.println("Invalid reservation number");
			return;
		}

		CheckInRoomForm checkInRoomForm = new CheckInRoomForm();
		checkInRoomForm.setReservationNumber(reservationNumber);

		String roomNumber = checkInRoomForm.checkIn();
		System.out.println("Check-in has been completed.");
		System.out.println("Room number is " + roomNumber + ".");

	}

        private void checkOutRoom() throws IOException, AppException {
                System.out.println("Input room number");
                System.out.print("> ");

		String roomNumber = reader.readLine();

		if (roomNumber == null || roomNumber.length() == 0) {
			System.out.println("Invalid room number");
			return;
		}

		CheckOutRoomForm checkoutRoomForm = new CheckOutRoomForm();
		checkoutRoomForm.setRoomNumber(roomNumber);
                checkoutRoomForm.checkOut();
                System.out.println("Check-out has been completed.");
        }

        private void cancelReservation() throws IOException, AppException {
                System.out.println("Input reservation number");
                System.out.print("> ");

                String reservationNumber = reader.readLine();

                if (reservationNumber == null || reservationNumber.length() == 0) {
                        System.out.println("Invalid reservation number");
                        return;
                }

                CancelReservationForm cancelForm = new CancelReservationForm();
                cancelForm.setReservationNumber(reservationNumber);
                cancelForm.cancel();
                System.out.println("Cancellation has been completed.");
        }

        private void showRoomStatus() throws AppException {
                CheckRoomStatusForm form = new CheckRoomStatusForm();
                java.util.List rooms = form.getStatuses();
                System.out.println("RoomNumber\tType\tStayingDate");
                for (int i = 0; i < rooms.size(); i++) {
                        domain.room.Room room = (domain.room.Room) rooms.get(i);
                        String dateStr = "";
                        if (room.getStayingDate() != null) {
                                dateStr = util.DateUtil.convertToString(room.getStayingDate());
                        }
                        System.out.println(room.getRoomNumber() + "\t" + room.getType() + "\t" + dateStr);
                }
        }

	public static void main(String[] args) throws Exception {
		CUI cui = new CUI();
		cui.execute();
	}
}
