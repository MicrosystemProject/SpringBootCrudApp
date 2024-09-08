package code.microsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import code.microsystem.entity.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.pname=?1 and p.age=?2")
    List<Patient> findByPnameAndAge(String pname, int age);
// repository
    
	Optional<Patient> findByPname(String pname);
}
