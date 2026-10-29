package com.pets.listeners;

import org.testng.ITestListener;
import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Allure;

public class AllureListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result){

        Throwable throwable = result.getThrowable();
        if (throwable != null){
            Allure.addAttachment("Failure Reason", throwable.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result){
        AllureUtils.addTestNgGroupsAsAllureTags(result);
        AllureUtils.write();
    }
}
