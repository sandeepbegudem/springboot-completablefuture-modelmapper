package com.codingwithsandeepb.patientapplication.service;

import com.codingwithsandeepb.patientapplication.dto.PatientDTO;
import com.codingwithsandeepb.patientapplication.entity.Patient;
import com.codingwithsandeepb.patientapplication.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImp implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private ModelMapper modelMapper;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

    }

    private PatientDTO convertEntityToDto(Patient patient) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO = modelMapper.map(
                patient, com.codingwithsandeepb.patientapplication.dto.PatientDTO.class);
        return patientDTO;
    }

    private Patient convertDtoToEntity(PatientDTO patientDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Patient patient = new Patient();
        patient = modelMapper.map(patientDTO, Patient.class);
        return patient;
    }

    public PatientDTO savePatientData(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);

        Patient savedPatient = patientRepository.save(patient);
        PatientDTO savedPatientDto = modelMapper.map(savedPatient, PatientDTO.class);

        return savedPatientDto;
    }

    public PatientDTO getPatientByPK(Long id) {
        Patient patientById = patientRepository.findById(id).orElse(null);
        PatientDTO retrievedPatient = modelMapper.map(patientById, PatientDTO.class);
        return retrievedPatient;
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }


    // completable future

    // save
    @Async
    @org.springframework.transaction.annotation.Transactional
    public CompletableFuture<List<PatientDTO>> savePatients(MultipartFile file) throws IOException {


        long startTime = System.currentTimeMillis();
        List<PatientDTO> patientFile = parseCSVFile(file);

        Patient patient = modelMapper.map(patientFile, Patient.class);

        List<Patient> savedPatients = patientRepository.saveAll(List.of(patient));
        logger.info("saving patient id: {}", patient.getPatientId());
        // list.add(savedPatients);
        //patientRepository.saveAll(add);

        //List<Patient> s = Collections.singletonList(patientRepository.saveAll(patient));
        logger.info("saving list of patients of size {}",
                patientFile.size(), "{}", Thread.currentThread().getName());
        PatientDTO savedPatientsDto = modelMapper.map(savedPatients, PatientDTO.class);

        // list.add((Patient) savedPatientDto);

        long endTime = System.currentTimeMillis();
        logger.info("time taken for saving data : {}", endTime - startTime);
        return CompletableFuture.completedFuture(Collections.singletonList(savedPatientsDto));
    }

    //util method
    private List<PatientDTO> parseCSVFile(MultipartFile file) throws IOException {

        final List<PatientDTO> patients = new ArrayList<>();
        try {
            final BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(),
                    StandardCharsets.UTF_8));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] data = line.split(",");
                final PatientDTO patientDTO = new PatientDTO();
                // patientDTO.setPatientId(Long.valueOf(data[0]));
                patientDTO.setFirstName(data[0]);
                patientDTO.setLastName(data[1]);
                patientDTO.setAge(data[2]);
                patientDTO.setCity(data[3]);
                patientDTO.setGender(data[4]);
                patientDTO.setEmail(data[5]);
                patientDTO.setPhone(data[6]);
                patients.add(patientDTO);

            }

            return patients;
        } catch (Exception exception) {
            logger.error("failed to parse CSV file {}", exception);

        }
        return patients;
    }

    // get all patients
    @Async
    public CompletableFuture<List<PatientDTO>> getAllPatientsUsingCF() {
        return (CompletableFuture<List<PatientDTO>>) patientRepository.findAll()
                .stream()
                .map(this::convertEntityToDtoCF)
                .collect(Collectors.toList());

    }

    private List<PatientDTO> convertEntityToDtoCF(Patient patient) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PatientDTO patientDTO = new PatientDTO();
        List<PatientDTO> patients = Collections.singletonList(modelMapper.map(
                patient, PatientDTO.class));
        return patients;
    }

}




