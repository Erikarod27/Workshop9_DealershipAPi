package com.pluralsight.DealershipAPI.models;

import com.pluralsight.DealershipAPI.models.abstractModels.Contract;

public class SalesContract extends Contract {
    private final double salesTaxPercentage;
    private final double recordingFee;
    private final double processingFee;
    private final boolean wouldLikeToFinance;
    private int loanTermInMonths = 0;

    public SalesContract(String dateOfContract, String customerName, String customerEmail, Vehicle vehicleSold, boolean wouldLikeToFinance) {
        super(dateOfContract, customerName, customerEmail, vehicleSold);
        this.salesTaxPercentage = vehicleSold.price() * .05;
        this.recordingFee = 100;
        if (vehicleSold.price() < 10_000) { //Calculate processing fee
            this.processingFee = 295;
        } else {
            this.processingFee = 495;
        }
        this.wouldLikeToFinance = wouldLikeToFinance;
    }


    //Methods
    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        totalPrice += getMonthlyPayment() * loanTermInMonths;
        totalPrice += recordingFee;
        totalPrice += processingFee;
        totalPrice *= salesTaxPercentage;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyInterestRate; //as decimal

        if (!this.wouldLikeToFinance) return 0;

        if (this.getVehicleSold().price() > 10_000) {
            monthlyInterestRate = 4.25 / 100 / 12;
            loanTermInMonths = 48;
        } else {
            monthlyInterestRate = 5.25 / 100 / 12;
            loanTermInMonths = 24;
        }
        return (monthlyInterestRate * this.getVehicleSold().price()) / (1 - Math.pow(1 + monthlyInterestRate, loanTermInMonths));
    }

    //Getters and Setters

    public boolean isWouldLikeToFinance() {
        return wouldLikeToFinance;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public double getSalesTaxPercentage() {
        return salesTaxPercentage;
    }

    public double getRecordingFee() {
        return recordingFee;
    }
}
