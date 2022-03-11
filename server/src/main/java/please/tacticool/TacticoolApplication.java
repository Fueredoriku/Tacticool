package please.tacticool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacticoolApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TacticoolApplication.class);
		app.run(args);
	}


}
