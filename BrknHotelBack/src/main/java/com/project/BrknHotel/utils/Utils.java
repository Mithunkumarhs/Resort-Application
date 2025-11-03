package com.project.BrknHotel.utils;

import com.project.BrknHotel.dto.BookingDTO;
import com.project.BrknHotel.dto.RoomDTO;
import com.project.BrknHotel.dto.UserDTO;
import com.project.BrknHotel.entity.Booking;
import com.project.BrknHotel.entity.Room;
import com.project.BrknHotel.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates a random alphanumeric booking confirmation code.
     */
    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    /**
     * Maps User entity to UserDTO (basic fields only).
     */
    public static UserDTO mapUserEntityToUserDTO(User user) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    /**
     * Maps Room entity to RoomDTO (basic fields only).
     */
    public static RoomDTO mapRoomEntityToRoomDTO(Room room) {
        if (room == null) return null;

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());
        return roomDTO;
    }

    /**
     * Maps Booking entity to BookingDTO (basic fields only).
     */
    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        if (booking == null) return null;

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        // Optional: include user and room
        if (booking.getUser() != null) {
            bookingDTO.setUser(mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getRoom() != null) {
            bookingDTO.setRoom(mapRoomEntityToRoomDTO(booking.getRoom()));
        }

        return bookingDTO;
    }

    /**
     * Maps Room entity to RoomDTO including its bookings (without user info).
     */
    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room) {
        if (room == null) return null;

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());

        if (room.getBookings() != null && !room.getBookings().isEmpty()) {
            roomDTO.setBookings(room.getBookings().stream()
                    .map(Utils::mapBookingEntityToBookingDTO)
                    .collect(Collectors.toList()));
        }

        return roomDTO;
    }

    /**
     * Maps Booking entity to BookingDTO with optional user and full room.
     */
    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedRooms(Booking booking, boolean mapUser) {
        if (booking == null) return null;

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        if (mapUser && booking.getUser() != null) {
            bookingDTO.setUser(mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getRoom() != null) {
            RoomDTO roomDTO = mapRoomEntityToRoomDTO(booking.getRoom());
            bookingDTO.setRoom(roomDTO);
        }

        return bookingDTO;
    }

    /**
     * Maps User entity to UserDTO including user's bookings and booked room info.
     */
    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndRoom(User user) {
        if (user == null) return null;

        UserDTO userDTO = mapUserEntityToUserDTO(user);

        if (user.getBookings() != null && !user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream()
                    .map(booking -> mapBookingEntityToBookingDTOPlusBookedRooms(booking, false))
                    .collect(Collectors.toList()));
        }

        return userDTO;
    }

    // ===================== LIST MAPPERS =======================

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream()
                .map(Utils::mapUserEntityToUserDTO)
                .collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList) {
        return roomList.stream()
                .map(Utils::mapRoomEntityToRoomDTO)
                .collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream()
                .map(Utils::mapBookingEntityToBookingDTO)
                .collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListToBookingDTOWithRooms(List<Booking> bookings) {
        return bookings.stream()
                .map(b -> mapBookingEntityToBookingDTOPlusBookedRooms(b, false))
                .collect(Collectors.toList());
    }
}
