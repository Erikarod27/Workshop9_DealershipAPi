package com.pluralsight.DealershipAPI.ui;

import com.pluralsight.dataHandlers.ContractDAO;
import com.pluralsight.dataHandlers.VehicleDAO;
import com.pluralsight.models.LeaseContract;
import com.pluralsight.models.SalesContract;
import com.pluralsight.models.Vehicle;
import com.pluralsight.util.Inputs;
import com.pluralsight.util.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractInterface {

    public void processSaleOrLease(VehicleDAO vehicleDAO) {
        ContractDAO contractDataManager = new ContractDAO();
        Text.createBigBlankSpace();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateOfContract = LocalDate.now().format(dtf);

        System.out.print("Enter customer name: ");
        String customerName = Inputs.getString();

        System.out.print("Enter customer email: ");
        String customerEmail = Inputs.getString();

        System.out.print("Enter VIN of vehicle sold: ");
        int vehicleVin = Inputs.getInt();
        Vehicle vehicle = vehicleDAO.filterByVin(vehicleVin);

        System.out.println(vehicle);

        boolean isChoosingSaleOrLease = true;
        while (isChoosingSaleOrLease) {
            System.out.print("""
                    Is this a sale or a lease, please select an option

                    [1] Sale
                    [2] Lease

                    Enter choice:\s""");
            int saleOrLease = Inputs.getInt();

            switch (saleOrLease) {
                case 1:
                    System.out.print("Would the customer like to finance their car?: ");
                    String wouldLikeToFinance = Inputs.getString().toLowerCase();
                    boolean finance = false;

                    switch (wouldLikeToFinance) {
                        case "y", "yes":
                            finance = true;
                            break;
                        case "n", "no":
                            break;
                        default:
                            System.out.println("This is not a valid choice");
                            Inputs.awaitInput();
                            break;
                    }
                    SalesContract salesContract = new SalesContract(dateOfContract, customerName, customerEmail, vehicle, finance);
                    contractDataManager.saveContract(salesContract);
                    break;
                case 2:
                    LeaseContract leaseContract = new LeaseContract(dateOfContract, customerName, customerEmail, vehicle);
                    contractDataManager.saveContract(leaseContract);
                    break;
                default:
                    System.out.println("This is not a valid choice");
                    Inputs.awaitInput();
                    break;
            }
            isChoosingSaleOrLease = false;
        }


        vehicleDAO.updateVehicleAsSold(vehicle);
    }
}
