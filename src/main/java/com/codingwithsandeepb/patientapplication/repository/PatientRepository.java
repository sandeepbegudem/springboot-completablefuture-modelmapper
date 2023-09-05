package com.codingwithsandeepb.patientapplication.repository;

import com.codingwithsandeepb.patientapplication.dto.PatientDTO;
import com.codingwithsandeepb.patientapplication.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

//    List<PatientDTO> saveAllPatients(PatientDTO patientDTO);

}
