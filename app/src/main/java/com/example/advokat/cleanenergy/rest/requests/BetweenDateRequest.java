package com.example.advokat.cleanenergy.rest.requests;

import com.example.advokat.cleanenergy.entities.AccessKeyDto;

public class BetweenDateRequest {

    private AccessKeyDto accessKeyDto;
    private Integer[] year; /*2015 - 2016*/
    private Integer[] month; /*11 - 8*/

    public AccessKeyDto getAccessKeyDto() {
        return accessKeyDto;
    }

    public void setAccessKeyDto(AccessKeyDto accessKeyDto) {
        this.accessKeyDto = accessKeyDto;
    }

    public Integer[] getYear() {
        return year;
    }

    public void setYear(Integer[] year) {
        this.year = year;
    }

    public Integer[] getMonth() {
        return month;
    }

    public void setMonth(Integer[] month) {
        this.month = month;
    }
}
