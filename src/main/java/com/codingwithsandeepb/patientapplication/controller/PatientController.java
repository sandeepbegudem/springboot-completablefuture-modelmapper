package com.codingwithsandeepb.patientapplication.controller;

import com.codingwithsandeepb.patientapplication.dto.PatientDTO;
import com.codingwithsandeepb.patientapplication.service.PatientServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
public class PatientController {

    @Autowired
    private PatientServiceImp patientServiceImp;

    @GetMapping("/all-patients")
    public ResponseEntity<List<PatientDTO>> retrieveAllPatientsList(){
        return new ResponseEntity<>(patientServiceImp.getAllPatients(), HttpStatus.OK);
    }
    @PostMapping("/save-patient")
    public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO){

        return new ResponseEntity<>(patientServiceImp.savePatientData(patientDTO), HttpStatus.OK);
    }

    @GetMapping("/get-patient/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id){

        return new ResponseEntity<>(patientServiceImp.getPatientByPK(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete-patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePatientById(@PathVariable Long id){
        patientServiceImp.deleteById(id);
    }

    @PostMapping(value = "/save-patients-threads",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity savePatientsThread(@RequestParam(value = "files") MultipartFile[] files)
            throws Exception {
        for (MultipartFile file : files) {
            patientServiceImp.savePatients(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/patients-threads", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        return  patientServiceImp.getAllPatientsUsingCF().thenApply(ResponseEntity::ok);
    }


    @GetMapping(value = "/patients-by -threads", produces = "application/json")
    public  ResponseEntity getUsers(){
        CompletableFuture<List<PatientDTO>> patients1=patientServiceImp.getAllPatientsUsingCF();
        CompletableFuture<List<PatientDTO>> patients2=patientServiceImp.getAllPatientsUsingCF();
        CompletableFuture<List<PatientDTO>> patients3=patientServiceImp.getAllPatientsUsingCF();
        CompletableFuture.allOf(patients1,patients2,patients3).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

}
