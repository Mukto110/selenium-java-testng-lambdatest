package com.lambdatest.framework.utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    //DataProvider 1 -> is for login data
    @DataProvider(name = "LoginData")
    public String [][] getData() throws IOException {
        String path = System.getProperty("user.dir") + "/src/main/resources/LoginData.xlsx";
        ExcelUtility xlUtil = new ExcelUtility(path);
        int totalRows = xlUtil.getRowCount("Sheet1");
        int totalCols = xlUtil.getCellCount("Sheet1",1);
        String[][] loginData = new String[totalRows][totalCols];

        for(int i=1;i<=totalRows;i++)
        {
            for(int j=0;j<totalCols;j++)
            {
                loginData[i-1][j]= xlUtil.getCellData("Sheet1",i, j);  // 1, 0
            }
        }
        return loginData;
    }

    // DataProvider 2

    // DataProvider 3
}
