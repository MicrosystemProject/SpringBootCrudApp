package code.microsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
		info = @Info(title = "AyushPlus App",version ="1.0v",
				description = "This Health Care domain Project.Develop By Code Microsystem"
					)
	//	@Contact(name = "Arvind Shamra",email = "codemicrosystem@gmail.com",url = "http://www.codemicrosystem.com")

)
//System.out.pritnln("Hello");
public class SpringBootCrudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudAppApplication.class, args);
	}

}
