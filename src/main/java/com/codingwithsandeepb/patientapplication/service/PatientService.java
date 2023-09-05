package com.codingwithsandeepb.patientapplication.service;

import com.codingwithsandeepb.patientapplication.dto.PatientDTO;

import java.util.List;

public interface PatientService {

    List<PatientDTO> getAllPatients();

    PatientDTO savePatientData(PatientDTO patientDTO);

    PatientDTO getPatientByPK(Long id);

    void deleteById(Long id);
}
