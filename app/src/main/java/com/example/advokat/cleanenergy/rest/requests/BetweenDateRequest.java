package com.example.advokat.cleanenergy.rest.requests;

import com.example.advokat.cleanenergy.entities.AccessKeyDto;
import com.example.advokat.cleanenergy.entities.DateFilter;
import com.example.advokat.cleanenergy.entities.RealmInteger;
import com.example.advokat.cleanenergy.utils.PreferenceManager;

import io.realm.RealmList;

public class BetweenDateRequest {

    private AccessKeyDto accessKeyDto;
    private Integer[] year; /*2015 - 2016*/
    private Integer[] month; /*11 - 8*/

    public static BetweenDateRequest getBetweenDataRequest(DateFilter dateFilter) {
        RealmList<RealmInteger> listYear = dateFilter.getYear();
        Integer[] arrYear = new Integer[listYear.size()];
        int i = 0;
        for (RealmInteger realmInteger : listYear) {
            arrYear[i] = realmInteger.getValue();
            i++;
        }
        RealmList<RealmInteger> listMonth = dateFilter.getMonth();
        Integer[] arrMonth = new Integer[listMonth.size()];
        int j = 0;
        for (RealmInteger realmInteger : listMonth) {
            arrMonth[j] = realmInteger.getValue() - 1;
            j++;
        }
        BetweenDateRequest betweenDateRequest = new BetweenDateRequest();
        AccessKeyDto accessKeyDto = new AccessKeyDto(PreferenceManager.getAccessToken());
        betweenDateRequest.setAccessKeyDto(accessKeyDto);
        betweenDateRequest.setYear(arrYear);
        betweenDateRequest.setMonth(arrMonth);
        return betweenDateRequest;
    }

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
