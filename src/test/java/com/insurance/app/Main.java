package com.insurance.app;

import com.insurance.app.service.InsuranceCalculationService;
import com.insurance.app.service.InsuranceCalculationServiceTest;
import org.junit.internal.TextListener;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runners.model.InitializationError;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@SpringBootTest
public class Main {


    public static void main(String[] args) {
        System.out.println("Running tests of SpringSampleTest !");
        Result jUnitCore = null;
        try {
            jUnitCore = new JUnitCore().run(new SpringJUnit4ClassRunner(InsuranceCalculationServiceTest.class));
        } catch (InitializationError e) {
            e.printStackTrace();
        }
//        jUnitCore.addListener(new TextListener(System.out));
//        jUnitCore.run(InsuranceCalculationServiceTest.class);
    }
}
