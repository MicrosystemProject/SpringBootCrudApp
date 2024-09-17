package code.microsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import code.microsystem.exception.PatientNotFoundException;
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

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	@Test
	public void getetAllPatientTest(){
		when(patientRepository.findAll()).thenReturn(Stream
				.of(new Patient(101,"Sunita",23,"sunita@gmail.com","9822939054","12345","nagpur",2300),
						new Patient(102,"Savita",23,"sunita@gmail.com","9822939054","12345","nagpur",2300)).collect(Collectors.toList()));
		assertEquals(2, patientService.getAllPatient().size());

	}

	@Test
	public void getPatientByIdTest() throws PatientNotFoundException {
		Long patientId = 101L;
		Patient patient = new Patient();
		patient.setPid(patientId);

		when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

		// Act
		Patient result = patientService.getPatientById(patientId);

		// Assert
		assertNotNull(result);
		assertEquals(patientId, result.getPid());
		verify(patientRepository, times(1)).findById(patientId);
	}

	@Test
	public void deletePatientByIdTest() throws PatientNotFoundException {
		Long patientId = 1L;
		Patient patient = new Patient();
		patient.setPid(patientId);
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
		// Act
		patientService.deletePatientById(patientId);
		// Assert
		verify(patientRepository, times(1)).findById(patientId);
		verify(patientRepository, times(1)).deleteById(patientId);
	}
}
