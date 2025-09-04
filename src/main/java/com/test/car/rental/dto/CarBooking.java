package com.test.car.rental.dto;

import java.util.Date;

public record CarBooking (Car car, User user, Date startDate, Date endDate){
}
