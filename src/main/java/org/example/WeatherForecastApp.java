package org.example;

import java.util.*;

public class WeatherForecastApp {
    private static final Map<String, List<Integer>> forecasts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Введите название города (или 'выход' для завершения):");

        while (true) {
            System.out.print("Город: ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("выход")) {
                System.out.println("До свидания!");
                break;
            }

            try {
                if (!city.matches("[А-Яа-яЁё]+")) {
                    throw new MyCustomException();
                }

                List<Integer> temperatures = getOrGenerateTemperatures(city);
                System.out.println("Прогноз температуры для города " + city + ": " + temperatures);
            } catch (MyCustomException e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
            }
        }
    }

    private static List<Integer> getOrGenerateTemperatures(String city) {
        if (forecasts.containsKey(city)) {
            return forecasts.get(city);
        } else {
            List<Integer> newTemperatures = generateTemperatures();
            forecasts.put(city, newTemperatures);
            return newTemperatures;
        }
    }

    private static List<Integer> generateTemperatures() {
        List<Integer> temperatures = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            temperatures.add(random.nextInt(20));
        }
        return temperatures;
    }
}