package com.matrix.mediconcallapp.configuration.enums;

import lombok.Getter;

@Getter
public enum AuthUrlMapping {
    DOCTOR(ROLE.ROLE_DOCTOR.name(), new String[] {
            "/contact/doctor",
            "/contact/doctor/**",
            "/reservation/doctor",
            "/reservation/doctor/**",
            "/doctor/info",
            "/doctor/edit",
            "/payment/doctor/**",
            "/medical-record/doctor/**",
            "/patient/doctor/**",
            "/rating/doctor",
            "/rating/doctor/**"
    }),
    PATIENT(ROLE.ROLE_PATIENT.name(), new String[] {
            "/contact/patient",
            "/contact/patient/**",
            "/reservation/patient",
            "/reservation/patient/**",
            "/patient/info",
            "/patient/edit",
            "/payment/pay",
            "/payment/patient/**",
            "/doctor/patient/**",
            "/medical-record/patient/**",
            "/rating/patient/**"
    }),
    ADMIN(ROLE.ROLE_ADMIN.name(), new String[] {
            "/doctor/admin/doctor/{id}",
            "/doctor/admin/doctors",
            "/reservation/admin/**",
            "/user/admin/**"

    }),
    PERMIT_ALL(null, new String[] {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/account/**",
            "/doctor/register",
            "/patient/register"
    }),

    ANY_AUTHENTICATED(null, new String[] {
            "/user/info",
            "/user/edit",
            "/user/password",
            "/doctor/specialties"
    });


    private final String role;
    private final String[] urls;

    AuthUrlMapping(String role, String[] urls) {
        this.role = role;
        this.urls = urls;
    }

}
