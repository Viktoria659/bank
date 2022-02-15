package com.example.bank.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

    public static String toJson(Object o) {
        try {
            return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException("can't convert object to string", e);
        }
    }
}
