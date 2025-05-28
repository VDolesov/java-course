package org.example;

import java.util.*;

public class WeatherForecastApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherRepository repository = new WeatherRepository();  // Подключение к БД

        System.out.println("Введите название города (или 'выход' для завершения):");

        while (true) {
            System.out.print("Город: ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("выход")) {
                System.out.println("До свидания!");
                break;
            }

            // Пытаемся получить прогноз из базы данных
            List<Integer> forecast = repository.getForecast(city);
            if (forecast.isEmpty()) {
                System.out.println("Прогноз не найден. Генерируем новый...");
                forecast = generateNewForecast();
                repository.saveForecast(city, forecast);  // Сохраняем в базе
            }

            System.out.println("Прогноз для города " + city + ": " + forecast);
        }
    }

    // Генерация случайных температур
    private static List<Integer> generateNewForecast() {
        Random random = new Random();
        List<Integer> forecast = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            forecast.add(random.nextInt(20));
        }
        return forecast;
    }
}
