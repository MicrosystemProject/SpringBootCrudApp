package code.microsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import code.microsystem.dto.PatientRequest;
import code.microsystem.entity.Patient;
import code.microsystem.repository.PatientRepository;
import code.microsystem.service.PatientService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class SpringBootCrudAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private PatientService patientService;

	@MockBean
	private PatientRepository patientRepository;

	@Test
	public void testPatientAdd() {

		Patient patient = Patient.builder().age(55).city("Nagpur").pname("Rutuja").email("rutuja123@gmail.com")
				.fees(1200).password("rutuja@123").mobile("9623309979").pid(101).build();
		PatientRequest patientRequest = new PatientRequest();
		patientRequest.setPid(patient.getPid());
		patientRequest.setPname(patient.getPname());
		patientRequest.setAge(patient.getAge());
		patientRequest.setEmail(patient.getEmail());
		patientRequest.setCity(patient.getCity());
		patientRequest.setMobile(patient.getMobile());
		patientRequest.setPassword(patient.getPassword());
		patientRequest.setMobile(patient.getMobile());

		when(patientRepository.save(any(Patient.class))).thenReturn(patient);
		assertEquals(patient, patientService.addPatient(patientRequest));

	}

}
