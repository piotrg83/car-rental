package com.test.car.rental.dto;

public record Checkout(Status status, long passedTimeDays) {
    public enum Status{OK, TIME_PASSED}
}
