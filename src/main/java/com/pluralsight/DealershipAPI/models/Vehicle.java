package com.pluralsight.DealershipAPI.models;

public record Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer,
                      double price, int sold) {
    @Override
    public String toString() {
        return String.format("""
            Vin: %d
            Year: %d
            Make: %s
            Model: %s
            Color: %s
            Vehicle Type: %s
            Odometer: %d
            Price: $%.2f
            Sold: %d
            """,
            vin(),
            year(),
            make(),
            model(),
            color(),
            vehicleType(),
            odometer(),
            price(),
            sold()
        );
    }
}
