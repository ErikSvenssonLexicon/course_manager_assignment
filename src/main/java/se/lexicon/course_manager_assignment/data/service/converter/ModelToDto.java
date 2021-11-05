package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelToDto implements Converters {
    @Override
    public StudentView studentToStudentView(Student student) {
        StudentView studentView = null;
        if(student != null){
            studentView = new StudentView(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getAddress()
            );
        }
        return studentView;
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        CourseView courseView = null;
        if(course != null){
            courseView = new CourseView(
                    course.getId(),
                    course.getCourseName(),
                    course.getStartDate(),
                    course.getWeekDuration(),
                    studentsToStudentViews(course.getStudents())
            );
        }
        return courseView;
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        List<CourseView> courseViews = new ArrayList<>();
        if(courses != null){
            for(Course course : courses){
                if(course != null){
                    courseViews.add(courseToCourseView(course));
                }
            }
        }
        return courseViews;
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        List<StudentView> studentViews = new ArrayList<>();
        if(students != null){
            for(Student student : students){
                if(student != null){
                    studentViews.add(studentToStudentView(student));
                }
            }
        }
        return studentViews;
    }
}
