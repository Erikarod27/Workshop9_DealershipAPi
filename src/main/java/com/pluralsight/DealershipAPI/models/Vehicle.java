package com.pluralsight.DealershipAPI.models;
import org.springframework.stereotype.Component;

@Component
public record Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer,
                      double price) {
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
            """,
            vin(),
            year(),
            make(),
            model(),
            color(),
            vehicleType(),
            odometer(),
            price());
    }
}
