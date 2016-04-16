package com.example.advokat.cleanenergy.utils;

import com.example.advokat.cleanenergy.entities.CurrentAssetsType;
import com.example.advokat.cleanenergy.entities.ExpenditureTypes;
import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;
import com.example.advokat.cleanenergy.entities.income.BuyerList;
import com.example.advokat.cleanenergy.entities.income.IncomeSourceList;
import com.example.advokat.cleanenergy.entities.income.IncomeTypesList;
import com.example.advokat.cleanenergy.entities.income.LocationsTypesList;
import com.example.advokat.cleanenergy.entities.income.ProductTypesList;

import java.util.List;

public class ListUtil {

    public static String[] getMeasureNames(List<MeasureUnit> list) {
        int listLength = list.size();
        String measureNames[] = new String[listLength];
        int i = 0;
        for (MeasureUnit item : list) {
            measureNames[i] = item.getName();
            i++;
        }
        return measureNames;
    }

    public static String[] getCurrentAssetsTypeNames(List<CurrentAssetsType> list) {
        int listLength = list.size();
        String currentNames[] = new String[listLength];
        int i = 0;
        for (CurrentAssetsType item : list) {
            currentNames[i] = item.getName();
            i++;
        }
        return currentNames;
    }

    public static String[] getExpenditureTypesNames(List<ExpenditureTypes> list) {
        int listLength = list.size();
        String expenditureNames[] = new String[listLength];
        int i = 0;
        for (ExpenditureTypes item : list) {
            expenditureNames[i] = item.getName();
            i++;
        }
        return expenditureNames;
    }

    public static String[] getPayers(List<Payer> list) {
        int listLength = list.size();
        String payerNames[] = new String[listLength];
        int i = 0;
        for (Payer item : list) {
            payerNames[i] = item.getName();
            i++;
        }
        return payerNames;
    }

    public static String[] getBuyers(List<BuyerList> list) {
        int listLength = list.size();
        String payerNames[] = new String[listLength];
        int i = 0;
        for (BuyerList item : list) {
            payerNames[i] = item.getName();
            i++;
        }
        return payerNames;

    }

    public static String[] getLocations(List<LocationsTypesList> list) {
        int listLength = list.size();
        String payerNames[] = new String[listLength];
        int i = 0;
        for (LocationsTypesList item : list) {
            payerNames[i] = item.getName();
            i++;
        }
        return payerNames;
    }

    public static String[] getIncomeTypes(List<IncomeTypesList> list) {
        int listLength = list.size();
        String payerNames[] = new String[listLength];
        int i = 0;
        for (IncomeTypesList item : list) {
            payerNames[i] = item.getName();
            i++;
        }
        return payerNames;
    }

    public static String[] getIncomeSources(List<IncomeSourceList> list) {
        int listLength = list.size();
        String payerNames[] = new String[listLength];
        int i = 0;
        for (IncomeSourceList item : list) {
            payerNames[i] = item.getName();
            i++;
        }
        return payerNames;
    }

    public static String[] getProductTypes(List<ProductTypesList> list) {
        int listLength = list.size();
        String payerNames[] = new String[listLength];
        int i = 0;
        for (ProductTypesList item : list) {
            payerNames[i] = item.getName();
            i++;
        }
        return payerNames;
    }
}
