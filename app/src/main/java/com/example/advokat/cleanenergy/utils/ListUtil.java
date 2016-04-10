package com.example.advokat.cleanenergy.utils;

import com.example.advokat.cleanenergy.entities.CurrentAssetsType;
import com.example.advokat.cleanenergy.entities.ExpenditureTypes;
import com.example.advokat.cleanenergy.entities.MeasureUnit;

import java.util.List;

public class ListUtil {

    public static String[] getMeasureNames(List<MeasureUnit> list) {
        int listLength = list.size();
        String measureNames[] = new String[listLength];
        int i = 0;
        for (MeasureUnit item: list) {
            measureNames[i] = item.getName();
            i++;
        }
        return measureNames;
    }

    public static String[] getCurrentAssetsTypeNames(List<CurrentAssetsType> list) {
        int listLength = list.size();
        String currentNames[] = new String[listLength];
        int i = 0;
        for (CurrentAssetsType item: list) {
            currentNames[i] = item.getName();
            i++;
        }
        return currentNames;
    }

    public static String[] getExpenditureTypesNames(List<ExpenditureTypes> list) {
        int listLength = list.size();
        String expenditureNames[] = new String[listLength];
        int i = 0;
        for (ExpenditureTypes item: list) {
            expenditureNames[i] = item.getName();
            i++;
        }
        return expenditureNames;
    }

}
