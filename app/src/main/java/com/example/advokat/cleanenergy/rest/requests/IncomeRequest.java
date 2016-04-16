package com.example.advokat.cleanenergy.rest.requests;

import com.example.advokat.cleanenergy.entities.AccessKeyDto;
import com.example.advokat.cleanenergy.entities.IncomeDTO;

public class IncomeRequest {

    private AccessKeyDto accessKeyDto;
    private IncomeDTO incomeDTO;

    public AccessKeyDto getAccessKeyDto() {
        return accessKeyDto;
    }

    public void setAccessKeyDto(AccessKeyDto accessKeyDto) {
        this.accessKeyDto = accessKeyDto;
    }

    public IncomeDTO getIncomeDTO() {
        return incomeDTO;
    }

    public void setIncomeDTO(IncomeDTO incomeDTO) {
        this.incomeDTO = incomeDTO;
    }
}
