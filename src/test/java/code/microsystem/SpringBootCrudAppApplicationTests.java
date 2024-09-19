package code.microsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import java.util.Arrays;
import java.util.List;
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
	public void getPatientByIdTest(){
		Patient patient = new Patient(101,"Sunita",23,"sunita@gmail.com","9822939054","12345","nagpur",2300);
		when(patientRepository.findById(101L)).thenReturn(java.util.Optional.of(patient));

		Patient retrievedPatient = patientRepository.findById(101L).orElse(null);

		assertNotNull(retrievedPatient);  // Ensure the patient is found
		assertEquals("sunita@gmail.com", retrievedPatient.getEmail());  // Check email
		assertEquals("9822939054", retrievedPatient.getMobile());       // Check mobile
	}
	@Test
	public void increaseFeesbycityTest() {
	    // Arrange: Create the patient objects with their initial fees
	    Patient patient1 = new Patient(101, "Sunita", 23, "sunita@gmail.com", "9822939054", "1504", "pune", 200);
	    Patient patient2 = new Patient(102, "Soonam", 25, "soonam@gmail.com", "9822938456", "4556", "pune", 230);
	    Patient patient3 = new Patient(103, "Sunita", 21, "sunita@gmail.com", "9822939054", "12345", "nagpur", 456);
	    
	    List<Patient> patients = Arrays.asList(patient1, patient2, patient3);
	    
	    // Mock the repository behavior
	    when(patientRepository.findAll()).thenReturn(patients);
	    when(patientRepository.saveAll(anyList())).thenReturn(patients);
	    
	    // Act: Call the method under test
	    List<Patient> updatedPatients = patientService.increaseFeesbycity();
	    
	    // Assert: Check if the fees for patients from Pune are correctly updated
	    assertEquals(240.0, updatedPatients.get(0).getFees()); // Patient 1: 200 + 20% = 240
	    assertEquals(276.0, updatedPatients.get(1).getFees()); // Patient 2: 230 + 20% = 276
	    assertEquals(456.0, updatedPatients.get(2).getFees()); // Patient 3: no change as city is not Pune
	    
	    // Verify that saveAll method was called once
	    verify(patientRepository, times(1)).saveAll(anyList());
	}

}
