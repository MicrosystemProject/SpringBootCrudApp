package code.microsystem.service;

import code.microsystem.dto.PatientRequest;
import code.microsystem.entity.Patient;
import code.microsystem.exception.PatientNotFoundException;

import java.util.List;

public interface PatientService {

	Patient addPatient(PatientRequest patientRequest);

    List<Patient> addPatientList(List<Patient> patients);

    Patient getByPatientId(long pId) throws PatientNotFoundException;

    List<Patient> getAllPatient();

    void deletePatientById(long pId) throws PatientNotFoundException;

    Patient editPatient(PatientRequest patientRequest) throws PatientNotFoundException;

    List<Patient> findByPnameAndAge(String pname, int age);

	Patient findEmailAndMobileByPname(String pname) throws PatientNotFoundException;

	Patient getPatientById(Long id) throws PatientNotFoundException;

	List<Patient> findPatientssortedByFees(String sortdirection) throws PatientNotFoundException;

}
