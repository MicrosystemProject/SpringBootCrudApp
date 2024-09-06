package code.microsystem.dto;

import jakarta.validation.constraints.*;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {
	private long pid;
	@NotNull(message = "Null value not allow in Patient Name")
	@NotBlank(message = "Patient Name should Not be Blank")
	private String pname;

	@Min(value = 1)
	@Max(value = 99)
	private int age;
	@NotBlank(message = "Email should Not be Blank")
	@Email(message = "Email is Invalid")
	private String email;
	@NotBlank(message = "Patient Mobile should Not be Blank")
	@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Nuber should be 10 digit long")
	private String mobile;
	@NotBlank(message = "Patient Password should Not be Blank")

	private String password;
}
