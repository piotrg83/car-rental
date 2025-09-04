package com.test.car.rental;

import com.test.car.rental.dto.Car;
import com.test.car.rental.dto.CarType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    public void createStore() {
        Car suv1 = new Car("KK 1", CarType.SUV);
        Car suv2 = new Car("KK 4", CarType.SUV);
        Car sedan = new Car("KK 1", CarType.SEDAN);
        Car van = new Car("KK 3", CarType.VAN);
        List<Car> cars = List.of(suv1, sedan, van, suv2);
        Set<Car> suvs = Set.of(suv1, suv2);
        Set<Car> sedans = Set.of(sedan);
        Set<Car> vans = Set.of(van);

        var result = Utils.createStore(cars);

        assertEquals(3, result.size());
        assertTrue(result.get(CarType.SUV).containsAll(suvs));
        assertTrue(result.get(CarType.VAN).containsAll(vans));
        assertTrue(result.get(CarType.SEDAN).containsAll(sedans));
    }

}