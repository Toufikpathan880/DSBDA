import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherAnalyzer {
    public static void main(String[] args) {
        String filename = "weather.txt";
        WeatherDataAverages averages = analyzeWeatherData(filename);
        if (averages != null) {
            System.out.println("Weather Data Analysis:");
            System.out.printf("Average Temperature: %.2f Celsius%n", averages.getAvgTemperature());
            System.out.printf("Average Dew Point: %.2f Celsius%n", averages.getAvgDewPoint());
            System.out.printf("Average Wind Speed: %.2f km/h%n", averages.getAvgWindSpeed());
        } else {
            System.out.println("No valid weather data found in the file.");
        }
    }

    public static WeatherDataAverages analyzeWeatherData(String filename) {
        List<Double> temperatures = new ArrayList<>();
        List<Double> dewPoints = new ArrayList<>();
        List<Integer> windSpeeds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    try {
                        double temperature = Double.parseDouble(data[2].trim());
                        double dewPoint = Double.parseDouble(data[3].trim());
                        int windSpeed = Integer.parseInt(data[4].trim());
                        temperatures.add(temperature);
                        dewPoints.add(dewPoint);
                        windSpeeds.add(windSpeed);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid data line: " + line);
                    }
                } else {
                    System.err.println("Skipping incomplete data line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            return null;
        }
        if (!temperatures.isEmpty()) {
            double sumTemp = 0;
            for (double temp : temperatures) {
                sumTemp += temp;
            }
            double avgTemp = sumTemp / temperatures.size();
            double sumDewPoint = 0;
            for (double dp : dewPoints) {
                sumDewPoint += dp;
            }
            double avgDewPoint = sumDewPoint / dewPoints.size();
            int sumWindSpeed = 0;
            for (int ws : windSpeeds) {
                sumWindSpeed += ws;
            }
            double avgWindSpeed = (double) sumWindSpeed / windSpeeds.size();
            return new WeatherDataAverages(avgTemp, avgDewPoint, avgWindSpeed);
        } else {
            return null;
        }
    }
}

class WeatherDataAverages {
    private double avgTemperature;
    private double avgDewPoint;
    private double avgWindSpeed;

    public WeatherDataAverages(double avgTemperature, double avgDewPoint, double avgWindSpeed) {
        this.avgTemperature = avgTemperature;
        this.avgDewPoint = avgDewPoint;
        this.avgWindSpeed = avgWindSpeed;
    }

    public double getAvgTemperature() {
        return avgTemperature;
    }

    public double getAvgDewPoint() {
        return avgDewPoint;
    }

    public double getAvgWindSpeed() {
        return avgWindSpeed;
    }
}