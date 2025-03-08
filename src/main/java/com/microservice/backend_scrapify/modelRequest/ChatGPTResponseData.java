package com.microservice.backend_scrapify.modelRequest;

import java.util.ArrayList;

public class ChatGPTResponseData {
    public String country_name;
    public String country_id;
    public String country_region;
    public String country_income_group;
    public String country_income_class;
    public String calling_code;
    public int mobile_min_length;
    public int mobile_max_length;
    public String currency_code;
    public String currency_symbol;
    public String time_zone;
    public String main_language;
    public String language_code;
    public ArrayList<String> domain_extensions;

    public ChatGPTResponseData() {
    }

    public ChatGPTResponseData(String country_name, String country_id, String country_region, String country_income_group, String country_income_class, String calling_code, int mobile_min_length, int mobile_max_length, String currency_code, String currency_symbol, String time_zone, String main_language, String language_code, ArrayList<String> domain_extensions) {
        this.country_name = country_name;
        this.country_id = country_id;
        this.country_region = country_region;
        this.country_income_group = country_income_group;
        this.country_income_class = country_income_class;
        this.calling_code = calling_code;
        this.mobile_min_length = mobile_min_length;
        this.mobile_max_length = mobile_max_length;
        this.currency_code = currency_code;
        this.currency_symbol = currency_symbol;
        this.time_zone = time_zone;
        this.main_language = main_language;
        this.language_code = language_code;
        this.domain_extensions = domain_extensions;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_region() {
        return country_region;
    }

    public void setCountry_region(String country_region) {
        this.country_region = country_region;
    }

    public String getCountry_income_group() {
        return country_income_group;
    }

    public void setCountry_income_group(String country_income_group) {
        this.country_income_group = country_income_group;
    }

    public String getCountry_income_class() {
        return country_income_class;
    }

    public void setCountry_income_class(String country_income_class) {
        this.country_income_class = country_income_class;
    }

    public String getCalling_code() {
        return calling_code;
    }

    public void setCalling_code(String calling_code) {
        this.calling_code = calling_code;
    }

    public int getMobile_min_length() {
        return mobile_min_length;
    }

    public void setMobile_min_length(int mobile_min_length) {
        this.mobile_min_length = mobile_min_length;
    }

    public int getMobile_max_length() {
        return mobile_max_length;
    }

    public void setMobile_max_length(int mobile_max_length) {
        this.mobile_max_length = mobile_max_length;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getMain_language() {
        return main_language;
    }

    public void setMain_language(String main_language) {
        this.main_language = main_language;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public ArrayList<String> getDomain_extensions() {
        return domain_extensions;
    }

    public void setDomain_extensions(ArrayList<String> domain_extensions) {
        this.domain_extensions = domain_extensions;
    }

    @Override
    public String toString() {
        return "ChatGPTResponseData{" +
                "country_name='" + country_name + '\'' +
                ", country_id='" + country_id + '\'' +
                ", country_region='" + country_region + '\'' +
                ", country_income_group='" + country_income_group + '\'' +
                ", country_income_class='" + country_income_class + '\'' +
                ", calling_code='" + calling_code + '\'' +
                ", mobile_min_length=" + mobile_min_length +
                ", mobile_max_length=" + mobile_max_length +
                ", currency_code='" + currency_code + '\'' +
                ", currency_symbol='" + currency_symbol + '\'' +
                ", time_zone='" + time_zone + '\'' +
                ", main_language='" + main_language + '\'' +
                ", language_code='" + language_code + '\'' +
                ", domain_extensions=" + domain_extensions +
                '}';
    }
}