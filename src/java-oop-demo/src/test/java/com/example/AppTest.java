
package com.example;

import org.junit.jupiter.api.Test;
import com.example.model.Student;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testMain() {
           // Here you would typically invoke the main method of App class
           // and verify the expected behavior, such as checking output or state.
           // This is a placeholder for the actual test implementation.
           Student student1 = new Student("Smith Jackson", 23, "S12345");
           student1.displayInfo();
    }
}