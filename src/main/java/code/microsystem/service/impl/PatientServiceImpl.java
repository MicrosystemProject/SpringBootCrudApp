package code.microsystem.service.impl;

import code.microsystem.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
		System.out.println("This service addPatient Lists");
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


//	@Override
//	public List<Patient> addPatientList(List<PatientRequest> patients) {
//		List<Patient> list=new ArrayList<Patient>();
//		list.addAll(patients);
//		return patientRepository.saveAll(list);
//	}

}
