package com.codingwithsandeepb.patientapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Patient {

    @Id
    //@Column(name = "patient_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    private Long patientId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private String age;
    @Column(name = "patient_gender")
    private String gender;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "country")
    private String country;
    @Column(name = "returning_patient")
    private Boolean returningPatient;
    @Column(name = "health_insurance_provider")
    private String insuranceProviderName;
    @Column(name = "admitted_reason")
    private String reasonForAdmission;
    @Column(name = "any_health_conditions")
    private String anyHealthConditions;
}
