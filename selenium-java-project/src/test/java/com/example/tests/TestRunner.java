package com.example.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner - executes all POM-based test classes
 * Run with: java -cp "target/test-classes;lib/*" com.example.tests.TestRunner
 */
public class TestRunner {
    public static void main(String[] args) {
        // Define all test classes to run
        Class<?>[] testClasses = {
            SeleniumTest.class,
            LoginTest.class,
            GoogleSearchTest.class
        };
        
        // Run all test classes
        System.out.println("========================================");
        System.out.println("Running POM Framework Tests");
        System.out.println("========================================\n");
        
        Result result = JUnitCore.runClasses(testClasses);
        
        // Print test results
        System.out.println("\n========================================");
        System.out.println("Test Results Summary:");
        System.out.println("========================================");
        System.out.println("Total tests run: " + result.getRunCount());
        System.out.println("Tests passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Tests failed: " + result.getFailureCount());
        System.out.println("Tests ignored: " + result.getIgnoreCount());
        System.out.println("Run time: " + result.getRunTime() + "ms");
        System.out.println("Success: " + (result.wasSuccessful() ? "YES" : "NO"));
        
        // Print failures if any
        if (result.getFailureCount() > 0) {
            System.out.println("\n========================================");
            System.out.println("Failure Details:");
            System.out.println("========================================");
            for (Failure failure : result.getFailures()) {
                System.out.println("\nTest: " + failure.getTestHeader());
                System.out.println("Exception: " + failure.getException().getMessage());
                System.out.println("Trace:\n" + failure.getTrace());
            }
        }
        
        // Exit with status code
        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}