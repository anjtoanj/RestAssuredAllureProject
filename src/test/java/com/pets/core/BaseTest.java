package com.pets.core;

import com.pets.config.ConfigBuilder;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;


public abstract class BaseTest implements IHookable {

    private final ThreadLocal<SoftAssert> testSoftAssert = new ThreadLocal<>();
    public String bancsDate;

    @BeforeClass(alwaysRun = true)
    public void setup(){
        RestAssured.baseURI = ConfigBuilder.getBaseUrl();
        //disable strict cert validation
        //RestAssure.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeMethod(alwaysRun = true)
    public void initialiseSoftAssert(){
        testSoftAssert.set(new SoftAssert());
    }

    // run methos acts as a wrapper around your test execution. It captures failures, updates the TestNG result and propagates the failure
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        SoftAssert softAssert = testSoftAssert.get();
        if (softAssert == null) {
            throw new IllegalThreadStateException("SoftAssert not initialised. check @BeforeMethod execution.");
        }

        try {
            callBack.runTestMethod(testResult);

        } catch (AssertionError assertionError) {
            if (testResult.getThrowable() == null) {
                testResult.setThrowable(assertionError);
            } else {
                testResult.getThrowable().addSuppressed(assertionError);
            }
        }

        testSoftAssert.remove();

        if (testResult.getThrowable() != null) {
            testResult.setStatus(ITestResult.FAILURE);

            if (testResult.getThrowable() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if (testResult.getThrowable() instanceof Error error) {
                throw error;
            }
            throw new RuntimeException(testResult.getThrowable());
        }
    }

    //Executes assertions inside an Allure reposting step
    protected void softAssertStep(String stepName, StepAssertion stepAssertion){
        try{
            Allure.step(stepName, () ->{
                SoftAssert stepSoftAssert = new SoftAssert();
                stepAssertion.execute(stepSoftAssert);
                stepSoftAssert.assertAll();
            });
        } catch (AssertionError assertionError){
            currentSoftAssert().fail(assertionError.getMessage());
        } catch (Exception exception){
            throw new RuntimeException("Failed to execute step: " + stepName, exception);

        }
    }
  //Retrieves the SoftAssert object for current test
    protected SoftAssert currentSoftAssert(){
        SoftAssert softAssert =testSoftAssert.get();
        if(softAssert == null){
            throw new IllegalThreadStateException("SoftAssert is not initialised for this test.");
        }
        return softAssert;
    }

    //Added to avoid error message during compile - Target type of a lamba conversion must be an interface wth a single abstract method
    @FunctionalInterface
    protected  interface StepAssertion{
        void execute(SoftAssert softAssert) throws Exception;
    }
}


//setup()	->Configure API testing
//initialiseSoftAssert()	->Create a fresh assertion object
//run()	->Control test execution and failures
//softAssertStep()	->Create Allure steps and execute assertions
//currentSoftAssert()	->Get the current test's assertion object
//StepAssertion	->Define the lambda structure
//ThreadLocal	->Keep test data separate between parallel threads
//assertAll()	->Collect and report all SoftAssert failures
//remove()	->Clean up ThreadLocal data