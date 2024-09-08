package code.microsystem.controller;

import code.microsystem.exception.PatientNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import code.microsystem.dto.PatientRequest;
import code.microsystem.entity.Patient;
import code.microsystem.service.PatientService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patient") 
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@GetMapping("/getHealth")
	public ResponseEntity<String> getHealth(){
		
		return new ResponseEntity<String>("SpringBoot Crud Application Running with good Health..",HttpStatus.OK);
	}


	@PostMapping("/addPatient")
	public ResponseEntity<Patient> addPatient(@RequestBody @Valid  PatientRequest patientRequest){
		Patient patient=patientService.addPatient(patientRequest);
		
		return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);
	}

	@PostMapping("/addPatientList")
	public ResponseEntity<List<Patient>> addPatientList(@RequestBody @Valid  List<Patient> patients){
		List<Patient> patientList=patientService.addPatientList(patients);
		return new ResponseEntity<List<Patient>>(patientList,HttpStatus.CREATED);
	}

	//Find By Patient Id
	@GetMapping("/getByPatientId/{id}")
	public ResponseEntity<Patient> getByPatientId(@PathVariable("id") long pId) throws PatientNotFoundException {
		Patient patient=patientService.getByPatientId(pId);
		if(patient==null) {
			throw new PatientNotFoundException("Patient Id does not exists :"+pId);
		}
        return new ResponseEntity<>(patient,HttpStatus.OK);
	}

	@GetMapping("/getAllPatient")
	public ResponseEntity<List<Patient>> getAllPatient(){
		List<Patient> patients=patientService.getAllPatient();
		return  new ResponseEntity<List<Patient>>(patients,HttpStatus.OK);
	}

	//Remove record by Id
	@DeleteMapping("/deletePatientById/{id}")
	public ResponseEntity<String> deletePatientById(@PathVariable("id") long pId) throws PatientNotFoundException {
		patientService.deletePatientById(pId);
		return new ResponseEntity<>("Patient Record Deleted :"+pId,HttpStatus.ACCEPTED);
	}

	//Remove record by Id using @RequestParam
	@DeleteMapping("/deletePatient")
	public ResponseEntity<String> deletePatient(@RequestParam("id") long pId) throws PatientNotFoundException {
		patientService.deletePatientById(pId);
		return new ResponseEntity<>("Patient Record Deleted :"+pId,HttpStatus.ACCEPTED);
	}

	//Edit or update  operation
	@PutMapping("/editPatient")
	public ResponseEntity<Patient> editPatient(@RequestBody @Valid  PatientRequest patientRequest) throws PatientNotFoundException {
		Patient patient=patientService.editPatient(patientRequest);
		return new ResponseEntity<Patient>(patient,HttpStatus.OK);
	}

	//findbypatient Name and age
	@GetMapping("/findByPnameAndAge")
	public ResponseEntity<List<Patient>> findByPnameAndAge(@RequestParam("pname") String pname,@RequestParam("age") String age) throws PatientNotFoundException {
		List<Patient> list=new ArrayList<>();
		list=patientService.findByPnameAndAge(pname,Integer.parseInt(age));
		if(list.isEmpty())
			throw new PatientNotFoundException("Patient Record Not Found.......");
		return new ResponseEntity<List<Patient>>(list, HttpStatus.OK);
	}
	
	// Find by email or mobile Number
	// find Email and Mobile
		@GetMapping("getPatientEmailAndMobileUsingId/{id}")
		public ResponseEntity<Map<String,String>>getPatientDetails(@PathVariable Long id) throws PatientNotFoundException{
			try {
				Patient patient = patientService.getPatientById(id);
				Map<String ,String > res = new HashMap<>();
				res.put("email", patient.getEmail());
				res.put("mobileNumber", patient.getMobile());
				return ResponseEntity.ok(res);
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Collections.singletonMap("error","Patient Id does not exists....."+id));
			}
		}

		@PutMapping("/increaseFeesForPunePatients")
		public ResponseEntity<List<Patient>> increaseFeesForPunePatients() {
		    List<Patient> updatedPatients = patientService.increaseFeesForPunePatients();
		    return new ResponseEntity<>(updatedPatients, HttpStatus.OK);
		}
	
}
