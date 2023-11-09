package com.matrix.mediconcallapp.configuration.enums;

import lombok.Getter;

@Getter
public enum AuthUrlMapping {
    DOCTOR(ROLE.ROLE_DOCTOR.name(), new String[] {
            "/contact/doctor",
            "/contact/doctor-view-contacts",
            "/contact/accept",
            "/reservations/doctor",
            "/reservations/view-request",
            "/reservations/accept",
            "/reservations/status",
            "/doctors/**",
            "/payment/doctor/**"
    }),
    PATIENT(ROLE.ROLE_PATIENT.name(), new String[] {
            "/contact/patient",
            "/contact/patient-view-contacts",
            "/contact/request",
            "/reservations/patient",
            "/reservations/times/**",
            "/reservations/request",
            "/reservations/cancel",
            "/patient/**",
            "/payment/pay",
            "/payment/patient/**"
    }),
    ADMIN(ROLE.ROLE_ADMIN.name(), new String[] {
            "/admin/**"
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
    }),

    ANY_AUTHENTICATED(null, new String[] {
            "/users/**",
    });


    private final String role;
    private final String[] urls;

    AuthUrlMapping(String role, String[] urls) {
        this.role = role;
        this.urls = urls;
    }

}
