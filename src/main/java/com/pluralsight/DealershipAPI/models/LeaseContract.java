package com.pluralsight.DealershipAPI.models;

import com.pluralsight.DealershipAPI.models.abstractModels.Contract;

public class LeaseContract extends Contract {
    private final double expectedEndingValue;
    private final double leaseFee;
    private final int loanTermInMonths = 36;

    public LeaseContract(String dateOfContract, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(dateOfContract, customerName, customerEmail, vehicleSold);

        this.expectedEndingValue = vehicleSold.price() * .50;
        this.leaseFee = vehicleSold.price() * .07;
    }

    //Methods
    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        totalPrice += getMonthlyPayment() * loanTermInMonths;
        totalPrice += leaseFee;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyInterestRate = 4.0 /100 /12; //as decimal

        return (monthlyInterestRate * this.getVehicleSold().price()) / (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));
    }

    //Getters and Setters

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }
}
