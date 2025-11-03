package com.project.BrknHotel.repo;

import com.project.BrknHotel.entity.Booking;
import com.project.BrknHotel.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

    List<Booking> findByUser(User user);

    List<Booking> findAllByUserEmail(String email);

}
