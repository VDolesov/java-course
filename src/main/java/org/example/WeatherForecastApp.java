package org.example;

import java.util.*;

public class WeatherForecastApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherRepository repository = new WeatherRepository();
        WeatherService service = new WeatherService(repository);

        System.out.println("Введите название города (или 'выход' для завершения):");

        while (true) {
            System.out.print("Город: ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("выход")) {
                System.out.println("До свидания!");
                break;
            }

            try {
                List<Integer> forecast = service.getForecast(city);
                System.out.println("Прогноз температуры для города " + city + ": " + forecast);
            } catch (InvalidCityNameException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
    }
}
