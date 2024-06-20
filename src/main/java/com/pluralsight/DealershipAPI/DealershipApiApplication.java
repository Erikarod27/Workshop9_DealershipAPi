package com.pluralsight.DealershipAPI;

import com.pluralsight.DealershipAPI.ui.DealershipInterface;
import com.pluralsight.DealershipAPI.util.Inputs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DealershipApiApplication {

	public static void main(String[] args) {
		Inputs.openScanner();
		SpringApplication.run(DealershipApiApplication.class, args);
		DealershipInterface userInterface = new DealershipInterface();
		userInterface.display();
		Inputs.closeScanner();
	}

}
