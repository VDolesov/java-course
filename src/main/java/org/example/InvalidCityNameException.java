package org.example;

public class InvalidCityNameException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Ошибка: название города должно содержать только русские буквы.";
    }
}
