package com.pluralsight.DealershipAPI.dataHandlers;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DataManager;
import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.VehicleDAO;
import com.pluralsight.DealershipAPI.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleDAOImpl extends DataManager implements VehicleDAO {

    @Autowired
    public VehicleDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    public List<Vehicle> getAllVehicles(int dealershipId) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement s = connection.createStatement();
            ResultSet cars = s.executeQuery(String.format("SELECT * FROM cars WHERE dealershipID = %d AND sold IS null", dealershipId));

            while (cars.next()) {
                Vehicle vehicle = getVehicleInfo(cars);
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public <T> List<Vehicle> getVehicles(String query, List<T> arguments) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);

//TODO      Extract this method somehow
            for (int i = 0; i < arguments.size(); i++) {
                T argument = arguments.get(i);
                if (argument instanceof Integer) {
                    ps.setInt(i + 1, (Integer) argument);
                } else if (argument instanceof Double) {
                    ps.setDouble(i + 1, (Double) argument);
                } else if (argument instanceof String) {
                    ps.setString(i + 1, (String) argument);
                } else {
                    throw new IllegalArgumentException("Unsupported argument type");
                }
            }
//            ps.setInt(arguments.size() + 1, dealershipId);

            ResultSet cars = ps.executeQuery();

            while (cars.next()) {
                Vehicle vehicle = getVehicleInfo(cars);
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public Vehicle filterByVin(int vin) {
        String query = "SELECT * FROM cars WHERE vin = ? AND dealershipID = ? AND sold IS NULL";
        return getVehicles(query, List.of(vin)).get(0);
    }

    public List<Vehicle> filterByPrice(double minimumPrice, double maximumPrice) {
        String query = "SELECT * FROM cars WHERE price BETWEEN ? AND ? AND dealershipID = ? AND sold IS NULL";
        return getVehicles(query, List.of(minimumPrice, maximumPrice));
    }

    public List<Vehicle> filterByMakeModel(String make, String model) {
        String query = "SELECT * FROM cars WHERE (make = ? OR model = ?) AND dealershipID = ? AND sold IS NULL";
        return getVehicles(query, List.of(make, model));
    }

    public List<Vehicle> filterByYear(int oldestYear, int newestYear) {
        String query = "SELECT * FROM cars WHERE year BETWEEN ? AND ? AND dealershipID = ? AND sold IS NULL";
        return getVehicles(query, List.of(oldestYear, newestYear));
    }

    public List<Vehicle> filterByColor(String color) {
        String query = "SELECT * FROM cars WHERE color = ? AND dealershipID = ? AND sold IS NULL";
        return getVehicles(query, List.of(color));
    }

    public List<Vehicle> filterByMileage(int highestMileage) {
        String query = "SELECT * FROM cars WHERE odometer < ? AND dealershipID = ? AND sold IS NULL";
        List<Integer> parameters = List.of(highestMileage);
        return getVehicles(query, parameters);
    }

    public List<Vehicle> filterByType(String type) {
        String query = "SELECT * FROM cars WHERE vehicleType = ? AND dealershipID = ? AND sold IS NULL";
        List<String> parameters = List.of(type);
        return getVehicles(query, parameters);
    }

    public void addVehicle(Vehicle vehicle) {
        String insertSql = "INSERT INTO cars (dealershipID, vin, year, make, model, color, odometer, vehicleType, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //add dealership id somehow
        List<?> parameters = List.of(vehicle.vin(), vehicle.year(), vehicle.make(), vehicle.model(), vehicle.color(), vehicle.odometer(), vehicle.vehicleType(), vehicle.price());
        executeUpdate(insertSql, parameters);
    }

    public void updateVehicleAsSold(Vehicle vehicle) {
        String updateSql = String.format("""
                UPDATE `cars`
                SET
                `sold` = 1
                WHERE `vin` = %d;
                """, vehicle.vin());
        executeUpdate(updateSql, List.of());
    }


    public void removeVehicle(Vehicle vehicle) {
        String deleteSql = "DELETE FROM cars WHERE vin = ?";
        List<?> parameters = List.of(vehicle.vin());
        executeUpdate(deleteSql, parameters);
    }

    private Vehicle getVehicleInfo(ResultSet cars) {
        try {
            int vin = cars.getInt("vin");
            int year = cars.getInt("year");
            String make = cars.getString("make");
            String model = cars.getString("model");
            String vehicleType = cars.getString("vehicleType");
            String color = cars.getString("color");
            int odometer = cars.getInt("odometer");
            double price = cars.getDouble("price");

            return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
