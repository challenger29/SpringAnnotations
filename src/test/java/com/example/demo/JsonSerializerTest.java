package com.example.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonSerializerTest {

    @Test
    public void serialize() {

        Car car = new Car("Ford", "F150", "2018");
        JsonSerializer serializer = new JsonSerializer();
        serializer.serialize(car);
    }
}