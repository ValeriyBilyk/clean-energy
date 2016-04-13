package com.example.advokat.cleanenergy.rest.requests;

import com.example.advokat.cleanenergy.entities.AccessKeyDto;
import com.example.advokat.cleanenergy.entities.ExpendituresDTO;

public class DataRequest {

    private AccessKeyDto accessKeyDto;
    private ExpendituresDTO expendituresDTO;

    public AccessKeyDto getAccessKeyDto() {
        return accessKeyDto;
    }

    public void setAccessKeyDto(AccessKeyDto accessKeyDto) {
        this.accessKeyDto = accessKeyDto;
    }

    public ExpendituresDTO getExpendituresDTO() {
        return expendituresDTO;
    }

    public void setExpendituresDTO(ExpendituresDTO expendituresDTO) {
        this.expendituresDTO = expendituresDTO;
    }
}
