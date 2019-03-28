package io.github.nowakprojects.learning.annotations;

import io.github.nowakprojects.learning.annotations.jsonserializer.Car;
import io.github.nowakprojects.learning.annotations.jsonserializer.JsonSerializeException;
import io.github.nowakprojects.learning.annotations.jsonserializer.JsonSerializer;

public class Main {
    public static void main(String[] args) throws JsonSerializeException {
        System.out.println("Hello world!");
        Car car = new Car("Fiat", "Panda", "2019");
        String jsonCar = JsonSerializer.serialize(car);
        System.out.println(jsonCar);
    }
}
