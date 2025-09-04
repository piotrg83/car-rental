package com.test.car.rental;

import com.test.car.rental.dto.Car;
import com.test.car.rental.dto.CarType;
import com.test.car.rental.dto.Checkout;
import com.test.car.rental.dto.User;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CarServiceTest {
    private final CarService carService = new CarService(List.of(new Car("KK1", CarType.VAN)));
    private final User user = new User("Jan", "Nowak");

    @Test
    public void bookCar() {
        Car bookedCar = carService.book(user, CarType.VAN, new Date(), 2);
        assertEquals(CarType.VAN, bookedCar.carType());
    }

    @Test
    public void cannotBookNotSupportedCarType() {
        try {
            carService.book(user, CarType.SUV, new Date(), 2);
            fail();
        } catch (IllegalStateException ignore) {
        }
    }

    @Test
    public void returnCar() {
        Car bookedCar = carService.book(user, CarType.VAN, new Date(), 2);
        Checkout checkout = carService.checkout(bookedCar);
        assertEquals(new Checkout(Checkout.Status.OK, 0), checkout);
    }

    @Test
    public void returnCarAfterEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        calendar.add(Calendar.HOUR, -1);
        Car bookedCar = carService.book(user, CarType.VAN, calendar.getTime(), 2);
        Checkout checkout = carService.checkout(bookedCar);
        assertEquals(new Checkout(Checkout.Status.TIME_PASSED, 4), checkout);
    }

}