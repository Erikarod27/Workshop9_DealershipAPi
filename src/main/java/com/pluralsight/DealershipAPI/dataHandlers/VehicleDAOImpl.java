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
import java.util.Map;

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
        String query = "SELECT * FROM cars WHERE vin = ? AND dealership_id = ? AND sold IS NULL";
        return getVehicles(query, List.of(vin)).get(0);
    }

    public List<Vehicle> filterByPrice(double minimumPrice, double maximumPrice) {
        String query = "SELECT * FROM cars WHERE price BETWEEN ? AND ? AND dealership_id = ? AND sold IS NULL";
        return getVehicles(query, List.of(minimumPrice, maximumPrice));
    }

    public List<Vehicle> filterByMakeModel(String make, String model) {
        String query = "SELECT * FROM cars WHERE (make = ? OR model = ?) AND dealership_id = ? AND sold IS NULL";
        return getVehicles(query, List.of(make, model));
    }

    public List<Vehicle> filterByYear(int oldestYear, int newestYear) {
        String query = "SELECT * FROM cars WHERE year BETWEEN ? AND ? AND dealership_id = ? AND sold IS NULL";
        return getVehicles(query, List.of(oldestYear, newestYear));
    }

    public List<Vehicle> filterByColor(String color) {
        String query = "SELECT * FROM cars WHERE color = ? AND dealership_id = ? AND sold IS NULL";
        return getVehicles(query, List.of(color));
    }

    public List<Vehicle> filterByMileage(double maxMile, double minMile) {
        String query = "SELECT * FROM cars WHERE odometer BETWEEN ? AND ? AND dealership_id = ? AND sold IS NULL";
        List<Double> parameters = List.of(maxMile, minMile);
        return getVehicles(query, parameters);
    }

    public List<Vehicle> filterByType(String type) {
        String query = "SELECT * FROM cars WHERE vehicleType = ? AND dealership_id = ? AND sold IS NULL";
        List<String> parameters = List.of(type);
        return getVehicles(query, parameters);
    }

    public void addVehicle(int dealership_id, Vehicle vehicle) {
        String insertSql = "INSERT INTO cars (dealership_id, vin, year, make, model, color, odometer, vehicleType, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //add dealership id somehow
        List<?> parameters = List.of(dealership_id, vehicle.vin(), vehicle.year(), vehicle.make(), vehicle.model(), vehicle.color(), vehicle.odometer(), vehicle.vehicleType(), vehicle.price());
        executeUpdate(insertSql, parameters);
    }

    public int updateVehicle(Vehicle vehicle) {
        String updateCarSql = "UPDATE cars SET year = ?, make = ?, model = ?, color = ?, odometer = ?, vehicleType = ?, price = ?, sold = ? WHERE vin = ?";
        List<?> parameters = List.of(vehicle.year(), vehicle.make(), vehicle.model(), vehicle.color(), vehicle.odometer(), vehicle.vehicleType(), vehicle.price(), vehicle.sold(), vehicle.vin());
        return executeUpdate(updateCarSql, parameters);
    }

    public int removeVehicle(int vin) {
        String deleteSql = "DELETE FROM cars WHERE vin = ?";
        List<?> parameters = List.of(vin);
        return executeUpdate(deleteSql, parameters);
    }

    @Override
    public List<Vehicle> filterVehicles(Map<String, Object> queryParams) {
        List<Vehicle> vehicles = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM vehicles WHERE ");

        query.append("dealership_id = ? ");
        params.add(queryParams.get("dealership_id"));
        if (queryParams.containsKey("make")) {
            query.append("AND `make` = ? ");
            params.add(queryParams.get("make"));
        }
        if (queryParams.containsKey("model")) {
            query.append("AND `model` = ? ");
            params.add(queryParams.get("model"));
        }
        if (queryParams.containsKey("year")) {
            query.append("AND `year` = ? ");
            params.add(queryParams.get("year"));
        }
        if (queryParams.containsKey("minOdometer")) {
            query.append("AND `odometer` > ? ");
            params.add(queryParams.get("minOdometer"));
        }
        if (queryParams.containsKey("maxOdometer")) {
            query.append("AND `odometer` < ? ");
            params.add(queryParams.get("maxOdometer"));
        }
        if (queryParams.containsKey("color")) {
            query.append("AND `color` = ? ");
            params.add(queryParams.get("color"));
        }
        if (queryParams.containsKey("vehicleType")) {
            query.append("AND `vehicleType` = ? ");
            params.add(queryParams.get("vehicleType"));
        }
        if (queryParams.containsKey("minPrice")) {
            query.append("AND `price` > ? ");
            params.add(queryParams.get("minPrice"));
        }
        if (queryParams.containsKey("maxPrice")) {
            query.append("AND `price` < ? ");
            params.add(queryParams.get("maxPrice"));
        }

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query.toString());

            for (int i = 0; i < queryParams.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                vehicles.add(getVehicleInfo(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
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
            int sold = cars.getInt("sold");

            return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
