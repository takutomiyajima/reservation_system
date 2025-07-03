/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.util.Date;

import util.DateUtil;
import domain.DaoFactory;
import domain.room.RoomDao;
import domain.room.RoomException;

/**
 * Manager for payments<br>
 * 
 */
public class PaymentManager {

	/**
	 * Fee per one night<br>
	 */
        private static final int RATE_STANDARD = 8000;
        private static final int RATE_SUITE = 12000;

        public void createPayment(Date stayingDate, String roomNumber) throws PaymentException,
                        NullPointerException {
                if (stayingDate == null) {
                        throw new NullPointerException("stayingDate");
                }
		if (roomNumber == null) {
			throw new NullPointerException("roomNumber");
		}

		Payment payment = new Payment();
		payment.setStayingDate(stayingDate);
		payment.setRoomNumber(roomNumber);
                try {
                        payment.setAmount(getRatePerDay(roomNumber));
                } catch (RoomException e) {
                        PaymentException pe = new PaymentException(PaymentException.CODE_DB_EXEC_QUERY_ERROR, e);
                        throw pe;
                }
		payment.setStatus(Payment.PAYMENT_STATUS_CREATE);

		PaymentDao paymentDao = getPaymentDao();
		paymentDao.createPayment(payment);
	}

        private int getRatePerDay(String roomNumber) throws RoomException {
                RoomDao roomDao = DaoFactory.getInstance().getRoomDao();
                domain.room.Room room = roomDao.getRoom(roomNumber);
                if (room == null) {
                        return RATE_STANDARD;
                }
                if (domain.room.RoomType.SUITE.equals(room.getType())) {
                        return RATE_SUITE;
                }
                return RATE_STANDARD;
        }

	public void consumePayment(Date stayingDate, String roomNumber) throws PaymentException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}
		if (roomNumber == null) {
			throw new NullPointerException("roomNumber");
		}

		PaymentDao paymentDao = getPaymentDao();
		Payment payment = paymentDao.getPayment(stayingDate, roomNumber);
		//If corresponding payment does not exist
		if (payment == null) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_PAYMENT_NOT_FOUND);
			exception.getDetailMessages().add("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			exception.getDetailMessages().add("room_number[" + roomNumber + "]");
			throw exception;
		}
		//If payment has been consumed already
		if (payment.getStatus().equals(Payment.PAYMENT_STATUS_CONSUME)) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_PAYMENT_ALREADY_CONSUMED);
			exception.getDetailMessages().add("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			exception.getDetailMessages().add("room_number[" + roomNumber + "]");
			throw exception;
		}
		
		payment.setStatus(Payment.PAYMENT_STATUS_CONSUME);
		paymentDao.updatePayment(payment);
	}

	private PaymentDao getPaymentDao() {
		return DaoFactory.getInstance().getPaymentDao();
	}
}
