package code.microsystem.controller;

import code.microsystem.exception.PatientNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
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
@Tag(name = "AyushPlus", description = "Patient Mangement API")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Operation(summary = "Get Application Health", description = "Get Application Health .")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Patient.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/getHealth")
	public ResponseEntity<String> getHealth() {

		return new ResponseEntity<String>("SpringBoot Crud Application Running with good Health..", HttpStatus.OK);
	}

	@Operation(summary = "Add Ne Patient..")
	@ApiResponses({
			@ApiResponse(responseCode = "201", content = {
					@Content(schema = @Schema(implementation = Patient.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/addPatient")
	public ResponseEntity<Patient> addPatient(@RequestBody @Valid PatientRequest patientRequest) {
		Patient patient = patientService.addPatient(patientRequest);

		return new ResponseEntity<Patient>(patient, HttpStatus.CREATED);
	}

	@Operation(summary = "Add Patient list", description = "Add multiple patients information at a time .")
	@ApiResponses({
			@ApiResponse(responseCode = "201", content = {
					@Content(schema = @Schema(implementation = Patient.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/addPatientList")
	public ResponseEntity<List<Patient>> addPatientList(@RequestBody @Valid List<Patient> patients) {
		List<Patient> patientList = patientService.addPatientList(patients);
		return new ResponseEntity<List<Patient>>(patientList, HttpStatus.CREATED);
	}

	@Operation(summary = "Retrieve a Patient by Id", description = "Get a Patient object by specifying its id. The response is Patient object .")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Patient.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/getByPatientId/{id}")
	public ResponseEntity<Patient> getByPatientId(@PathVariable("id") long pId) throws PatientNotFoundException {
		Patient patient = patientService.getByPatientId(pId);
		if (patient == null) {
			throw new PatientNotFoundException("Patient Id does not exists :" + pId);
		}
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}

	@Operation(summary = "Retrive All Patient Details", description = "Get all patient detals by sending this request")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(schema = @Schema(implementation = Patient.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/getAllPatient")
	public ResponseEntity<List<Patient>> getAllPatient() {
		List<Patient> patients = patientService.getAllPatient();
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}
    
	@Operation(summary = "Delete patient by Patient Id", description = "Delete patient by the patient Id")
	@ApiResponses({
		@ApiResponse(responseCode = "202",content= {
				@Content(schema=@Schema(implementation = Patient.class),mediaType="application/json")}),
		@ApiResponse(responseCode = "404", content = {
				@Content(schema = @Schema())}),
		@ApiResponse(responseCode = "500", content = {
				@Content(schema=@Schema())
		})
	})
	@DeleteMapping("/deletePatientById/{id}")
	public ResponseEntity<String> deletePatientById(@PathVariable("id") long pId) throws PatientNotFoundException {
		patientService.deletePatientById(pId);
		return new ResponseEntity<>("Patient Record Deleted :" + pId, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Delete patient by Patient Id using Parameter value", description = "Delete patient by the patient Id")
	@ApiResponses({
		@ApiResponse(responseCode = "202",content= {
				@Content(schema=@Schema(implementation = Patient.class),mediaType="application/json")}),
		@ApiResponse(responseCode = "404", content = {
				@Content(schema = @Schema())}),
		@ApiResponse(responseCode = "500", content = {
				@Content(schema=@Schema())
		})
	})
	@DeleteMapping("/deletePatient")
	public ResponseEntity<String> deletePatient(@RequestParam("id") long pId) throws PatientNotFoundException {
		patientService.deletePatientById(pId);
		return new ResponseEntity<>("Patient Record Deleted :" + pId, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Update patient by Patient Id using Parameter patientId", description = "Update user info using patient Id")
	@ApiResponses({
		@ApiResponse(responseCode = "200",content= {
				@Content(schema=@Schema(implementation = Patient.class),mediaType="application/json")}),
		@ApiResponse(responseCode = "404", content = {
				@Content(schema = @Schema())}),
		@ApiResponse(responseCode = "500", content = {
				@Content(schema=@Schema())
		})
	})
	@PutMapping("/editPatient")
	public ResponseEntity<Patient> editPatient(@RequestBody @Valid PatientRequest patientRequest)
			throws PatientNotFoundException {
		Patient patient = patientService.editPatient(patientRequest);
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	@Operation(summary = "Find patient name and age using Patient Id", description = "Get patient name and age using Patient Id")
	@ApiResponses({
		@ApiResponse(responseCode = "200",content= {
				@Content(schema=@Schema(implementation = Patient.class),mediaType="application/json")}),
		@ApiResponse(responseCode = "404", content = {
				@Content(schema = @Schema())}),
		@ApiResponse(responseCode = "500", content = {
				@Content(schema=@Schema())
		})
	})
	@GetMapping("/findByPnameAndAge")
	public ResponseEntity<List<Patient>> findByPnameAndAge(@RequestParam("pname") String pname,
			@RequestParam("age") String age) throws PatientNotFoundException {
		List<Patient> list = new ArrayList<>();
		list = patientService.findByPnameAndAge(pname, Integer.parseInt(age));
		if (list.isEmpty())
			throw new PatientNotFoundException("Patient Record Not Found.......");
		return new ResponseEntity<List<Patient>>(list, HttpStatus.OK);
	}

	
	@GetMapping("getPatientEmailAndMobileUsingId/{id}")
	public ResponseEntity<Map<String, String>> getPatientDetails(@PathVariable Long id)
			throws PatientNotFoundException {
		try {
			Patient patient = patientService.getPatientById(id);
			Map<String, String> res = new HashMap<>();
			res.put("email", patient.getEmail());
			res.put("mobileNumber", patient.getMobile());
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", "Patient Id does not exists....." + id));
		}
	}

}
