package com.pluralsight.DealershipAPI.ui;

import com.pluralsight.DealershipAPI.dataHandlers.DealershipDAO;
import com.pluralsight.DealershipAPI.dataHandlers.VehicleDAO;
import com.pluralsight.DealershipAPI.models.Dealership;
import com.pluralsight.DealershipAPI.models.Vehicle;
import com.pluralsight.DealershipAPI.util.*;

import java.util.List;

public class DealershipInterface {
    List<Dealership> dealerships;
    Dealership currentDealership;
    VehicleDAO vehicleDAO;

    private void init() {
        DealershipDAO dealershipDAO = new DealershipDAO();
        dealershipDAO.openConnection();
        this.dealerships = dealershipDAO.loadDealershipsFromDatabase();
        dealershipDAO.closeConnection();
        this.vehicleDAO = new VehicleDAO(currentDealership.dealershipID());
    }


    public void display() {
        //Initialize dealership
        init();
        chooseDealership();

        boolean searching = true;
        while (searching) {
            Text.createBigBlankSpace();
            System.out.printf(Text.centerMessage(" " + currentDealership.name() + " ", 50, '-'));
            System.out.println();
            System.out.printf(Text.centerMessage(" " + currentDealership.address() + " ", 50, '-'));
            System.out.print("""


                    What can we do for you today?

                    [1] Search vehicles in price range
                    [2] Find vehicles by make / model
                    [3] Find vehicles in year range
                    [4] Find vehicles by color
                    [5] Find vehicles in mileage range
                    [6] Find vehicles by type (Car, Truck, SUV, Van)
                    [7] List ALL vehicles

                    [8] Add vehicle
                    [9] Remove vehicle

                    [0] Sell/Lease Vehicle

                    [99] Quit

                    Enter choice:\s""");

            int vehicleSearchChoice = Inputs.getInt();

            switch (vehicleSearchChoice) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 0:
                    ContractInterface ci = new ContractInterface();
                    ci.processSaleOrLease(vehicleDAO);
                    break;
                case 99:
                    searching = false;
                    break;
            }
        }
    }

    private void chooseDealership() {
        System.out.println("""
                Which Dealership would you like to access?""");
        for (Dealership dealership : dealerships) {
            System.out.printf("[%d] %s\n%-4sAddress: %s\n\n", dealership.dealershipID(), dealership.name(), " ", dealership.address());
        }
        System.out.print("Enter choice: ");

        int userChoice = Inputs.getInt() - 1;

        if (dealerships.contains(dealerships.get(userChoice))) this.currentDealership = dealerships.get(userChoice);
        else {
            System.out.println("This dealership does not exist.");
            Inputs.awaitInput();
            display();
        }
    }


    private void displayVehicles(List<Vehicle> vehicles) {
        Text.createBigBlankSpace();
        System.out.println("\n\n\n");
        System.out.println(Text.centerMessage("Vehicles List", 30, '='));
        if (vehicles.isEmpty()) System.out.println("\n\nNo vehicles match the search...\n\n");
        for (Vehicle vehicle : vehicles) {
            System.out.println(Text.centerMessage(String.format("%s %s", vehicle.make(), vehicle.model()), 30, '='));
            System.out.printf("""
                    | Vin: %-21.21s |
                    | Price: %-19.19s |
                    | Color: %-19.19s |
                    | Type: %-20.20s |
                    | Mileage: %-17.17s |
                    | Year: %-20.20s |
                    """, vehicle.vin(), vehicle.price(), vehicle.color(), vehicle.vehicleType(), vehicle.odometer(), vehicle.year());
            System.out.println(Text.createLineofChars(30, '='));
            System.out.println("\n");
        }
        Inputs.awaitInput();
    }

    private void processAllVehiclesRequest() {
        Text.createBigBlankSpace();
        displayVehicles(vehicleDAO.getAllVehicles());
    }

    private void processGetByPriceRequest() {
        Text.createBigBlankSpace();
        System.out.print("Enter minumim price: ");
        double minPrice = Inputs.getDouble();

        System.out.print("Enter maximum price: ");
        double maxPrice = Inputs.getDouble();

        displayVehicles(vehicleDAO.filterByPrice(minPrice, maxPrice));
    }

    private void processGetByMakeModelRequest() {
        Text.createBigBlankSpace();
        System.out.println(Text.centerMessage("Leave search option blank if not needed", 40, '-'));
        System.out.print("Enter make to search for: ");
        String make = Inputs.getString();
        System.out.print("Enter model to search for: ");
        String model = Inputs.getString();
        displayVehicles(vehicleDAO.filterByMakeModel(make, model));
    }

    private void processGetByYearRequest() {
        Text.createBigBlankSpace();
        System.out.print("Enter oldest car year: ");
        int oldestYear = Inputs.getInt();

        System.out.print("Enter newest year: ");
        int newestYear = Inputs.getInt();

        displayVehicles(vehicleDAO.filterByYear(oldestYear, newestYear));
    }

    private void processGetByMileageRequest() {
        Text.createBigBlankSpace();
        System.out.print("Enter maximum mileage: ");
        int maxMileage = Inputs.getInt();

        displayVehicles(vehicleDAO.filterByMileage(maxMileage));
    }

    private void processGetByColorRequest() {
        Text.createBigBlankSpace();
        System.out.print("Enter vehicle color: ");
        String color = Inputs.getString();
        displayVehicles(vehicleDAO.filterByColor(color));
    }

    private void processGetByVehicleTypeRequest() {
        Text.createBigBlankSpace();
        System.out.print("Enter vehicle type: ");
        String vehicleType = Inputs.getString();
        displayVehicles(vehicleDAO.filterByType(vehicleType));
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        int vin = Inputs.getInt();
        for (Vehicle vehicle : this.vehicleDAO.getAllVehicles()) {
            if (vehicle.vin() == vin) {
                System.out.printf("""
                Is this the correct vehicle?
                
                %s
                
                Enter (y/n):\s""", vehicle);

                String userChoice = Inputs.getString();

                switch (userChoice) {
                    case "Y", "y", "yes", "Yes" -> vehicleDAO.removeVehicle(vehicle);
                    case "N", "n", "no", "No" -> processRemoveVehicleRequest();
                    default -> {
                        System.out.println("That is not a valid choice... Restarting process...");
                        processRemoveVehicleRequest();
                    }
                }
                System.out.println("\nSuccessfully removed vehicle from inventory.");
                Inputs.awaitInput();
            }
        }
    }

    private void processAddVehicleRequest() {
        System.out.print("Enter vehicle VIN: ");
        int vin = Inputs.getInt();

        System.out.print("Enter vehicle make: ");
        String make = Inputs.getString();

        System.out.print("Enter vehicle model: ");
        String model = Inputs.getString();

        System.out.print("Enter vehicle year: ");
        int year = Inputs.getInt();

        System.out.print("Enter vehicle type: ");
        String type = Inputs.getString();

        System.out.print("Enter vehicle color: ");
        String color = Inputs.getString();

        System.out.print("Enter vehicle mileage: ");
        int mileage = Inputs.getInt();

        System.out.print("Enter vehicle price: $");
        double price = Inputs.getDouble();

        Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);

        System.out.printf("""
                Is this information correct?
                
                %s
                
                Enter (y/n):\s""", newVehicle);

        String userChoice = Inputs.getString();

        switch (userChoice) {
            case "Y", "y", "yes", "Yes" -> this.vehicleDAO.addVehicle(newVehicle);
            case "N", "n", "no", "No" -> processAddVehicleRequest();
            default -> {
                System.out.println("That is not a valid choice... Restarting process...");
                processAddVehicleRequest();
            }
        }

        System.out.println("Vehicle successfully added!");
        Inputs.awaitInput();
    }


}
