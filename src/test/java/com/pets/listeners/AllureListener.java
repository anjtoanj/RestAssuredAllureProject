package com.pets.listeners;

import com.pets.utils.AllureUtils;
import org.testng.ITestListener;
import io.qameta.allure.Allure;
import org.testng.ITestResult;

// How does everything connect from AllureUtils ?
// AllureUtils class is just a helper. It won't run automatically.This listener calls it.
public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            Allure.addAttachment("Failure Reason", throwable.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        AllureUtils.addTestNgGroupsasAllureTags(result);
        AllureUtils.write();
    }
}
