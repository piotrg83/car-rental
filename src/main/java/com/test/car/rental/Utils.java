package com.test.car.rental;

import com.test.car.rental.dto.Car;
import com.test.car.rental.dto.CarType;
import com.test.car.rental.dto.Checkout;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Utils {

    public static Map<CarType, Queue<Car>> createStore(List<Car> carsInput) {
        return carsInput.stream().map(Set::of)
                .map(ConcurrentLinkedQueue::new)
                .collect(Collectors.toMap(cars -> cars.stream().findAny().orElseThrow().carType(),
                        cars -> cars, (cars1, cars2) -> {
                            Queue<Car> cars = new ConcurrentLinkedQueue<>();
                            cars.addAll(cars1);
                            cars.addAll(cars2);
                            return cars;
                        }));
    }

    public static Date calculateEndDate(Date startDate, int bookingTimeDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, bookingTimeDays);
        Date endDate = calendar.getTime();
        return endDate;
    }

    public static Checkout calculateCheckout(Date endDate) {
        long diffNowAndExpectedEndDate = new Date().getTime() - endDate.getTime();
        if (diffNowAndExpectedEndDate <= 0) {
            return new Checkout(Checkout.Status.OK, 0);
        }
        long afterTimeHours = (int) TimeUnit.HOURS.convert(diffNowAndExpectedEndDate, TimeUnit.MILLISECONDS);
        long afterTimeDays = afterTimeHours / 24 + (afterTimeHours % 24 > 0 ? 1 : 0);

        return new Checkout(Checkout.Status.TIME_PASSED, afterTimeDays);
    }

}
