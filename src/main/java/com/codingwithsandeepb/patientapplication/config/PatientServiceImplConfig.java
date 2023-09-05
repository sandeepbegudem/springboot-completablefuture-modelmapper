package com.codingwithsandeepb.patientapplication.config;

import com.codingwithsandeepb.patientapplication.service.PatientServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientServiceImplConfig {

    @Bean
    public PatientServiceImp patientServiceImpl() {
        return new PatientServiceImp();
    }
}
