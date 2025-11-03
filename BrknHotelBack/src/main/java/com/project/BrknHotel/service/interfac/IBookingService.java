package com.project.BrknHotel.service.interfac;

import com.project.BrknHotel.dto.Response;
import com.project.BrknHotel.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Booking booking, String userEmail);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response getMyBookings(String email);

    Response cancelBooking(Long bookingId);
}
