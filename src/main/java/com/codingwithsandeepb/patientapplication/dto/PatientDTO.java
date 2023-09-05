package com.codingwithsandeepb.patientapplication.dto;

import com.codingwithsandeepb.patientapplication.entity.Patient;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class PatientDTO implements Serializable {

    private Long patientId;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String email;
    private String phone;
    private String city;
}
