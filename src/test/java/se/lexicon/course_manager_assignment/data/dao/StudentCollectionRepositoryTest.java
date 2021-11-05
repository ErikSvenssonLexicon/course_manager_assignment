package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;
    private Student testStudent;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }

    //Write your tests here


    @BeforeEach
    void setUp() {
        testStudent = testObject.createStudent("Karmand Aziz", "karmand@gmail.com", "Storgatan 1, 34523, Växjö")  ;
    }

    @Test
    @DisplayName("Given testStudent.id findById return expected")
    void findById() {
        int id = testStudent.getId();

        Student result = testObject.findById(id);

        assertNotNull(result);
        assertEquals(testStudent, result);
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
