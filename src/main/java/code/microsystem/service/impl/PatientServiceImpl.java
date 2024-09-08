package code.microsystem.service.impl;

import code.microsystem.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import code.microsystem.dto.PatientRequest;
import code.microsystem.entity.Patient;
import code.microsystem.repository.PatientRepository;
import code.microsystem.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public Patient addPatient(PatientRequest patientRequest) {
		Patient patientObj=Patient.builder().pname(patientRequest.getPname()).age(patientRequest.getAge()).email(patientRequest.getEmail()).mobile(patientRequest.getMobile()).password(patientRequest.getPassword()).build();
		return patientRepository.save(patientObj);
	}

	@Override
	public List<Patient> addPatientList(List<Patient> patients) {
	     return patientRepository.saveAll(patients);
	}

	@Override
	public Patient getByPatientId(long pId) throws PatientNotFoundException {
		Optional<Patient> patientOptional =patientRepository.findById(pId);
		if(!patientOptional.isPresent())
			throw new PatientNotFoundException("Patient Id does not exists :"+pId);
		return patientOptional.get();
	}

	@Override
	public List<Patient> getAllPatient() {
		return patientRepository.findAll();
	}

	@Override
	public void deletePatientById(long pId) throws PatientNotFoundException {
		Optional<Patient> patientOptional =patientRepository.findById(pId);
		if(!patientOptional.isPresent())
			throw new PatientNotFoundException("Patient Id does not exists :"+pId);

		patientRepository.deleteById(pId);
	}

	@Override
	public Patient editPatient(PatientRequest patientRequest) throws PatientNotFoundException {
		Optional<Patient> patientOptional =patientRepository.findById(patientRequest.getPid());
		if(!patientOptional.isPresent())
			throw new PatientNotFoundException("Patient Id does not exists :"+patientRequest.getPid());
		Patient patientObj=patientOptional.get();
		 //patientObj=Patient.builder().pname(patientRequest.getPname()).age(patientRequest.getAge()).email(patientRequest.getEmail()).mobile(patientRequest.getMobile()).password(patientRequest.getPassword()).build();
		patientObj.setPname(patientRequest.getPname());patientObj.setEmail(patientRequest.getEmail());
		patientObj.setAge(patientRequest.getAge());patientObj.setMobile(patientRequest.getMobile());
		patientObj.setPassword(patientRequest.getPassword());
		return patientRepository.save(patientObj);
	}

	@Override
	public List<Patient> findByPnameAndAge(String pname, int age) {
		List<Patient> list=patientRepository.findByPnameAndAge(pname,age);
		return list;
	}
	//Code is run Success
	@Override 
	 public Patient findEmailAndMobileByPname(String pname) throws PatientNotFoundException { 
		 
	return patientRepository.findByPname(pname)
	  .orElseThrow(()->new PatientNotFoundException("Patient Not Found With name:"+ pname)); }

	@Override
	public Patient getPatientById(Long id) throws PatientNotFoundException {
	        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
	    }
		

}
