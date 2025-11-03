package com.project.BrknHotel.service.interfac;

import com.project.BrknHotel.dto.LoginRequest;
import com.project.BrknHotel.dto.Response;
import com.project.BrknHotel.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUser();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

    Response getUserBookingHistoryByEmail(String email);
}
