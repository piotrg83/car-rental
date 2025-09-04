package com.test.car.rental;

import com.test.car.rental.dto.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.test.car.rental.Utils.*;

public class CarService {
    private final Map<CarType, Queue<Car>> cars;
    private final Set<CarBooking> carBookings;

    public CarService(List<Car> carsInput) {
        this.cars = createStore(carsInput);
        this.carBookings = ConcurrentHashMap.newKeySet();
    }


    public Car book(User who, CarType carType, Date startDate, int bookingTimeDays) {
        Queue<Car> availableCars = cars.get(carType);
        if (availableCars == null) {
            throw new IllegalStateException("car type " + carType + " is not supported");
        }

        Car car = availableCars.poll();
        if (car == null) {
            return null;
        }
        Date endDate = calculateEndDate(startDate, bookingTimeDays);
        CarBooking carBooking = new CarBooking(car, who, startDate, endDate);
        carBookings.add(carBooking);

        return car;
    }


    public Checkout checkout(Car car) {
        CarBooking carBooking = carBookings.stream()
                .filter(cb -> cb.car().equals(car)).findAny().orElseThrow();
        carBookings.remove(carBooking);
        cars.get(car.carType()).add(carBooking.car());

        return calculateCheckout(carBooking.endDate());
    }

}
