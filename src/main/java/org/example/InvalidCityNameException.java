package org.example;

// Создаю мой кастомный эксепшн, где могу изменить некоторое поведение
public class InvalidCityNameException extends RuntimeException { // наследуюсь от RuntimeException, чтобы унаследовать стандатное поведение эксепшена
    @Override
    public String getMessage() {
        return "Ошибка: название города должно содержать только русские буквы."; //переопределяю текст исключения
    }
}
