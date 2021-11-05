package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course course = courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return converters.courseToCourseView(course);
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course course = courseDao.findById(form.getId());
        if(course != null){
            course.setCourseName(form.getCourseName().trim());
            course.setStartDate(form.getStartDate());
            course.setWeekDuration(form.getWeekDuration());
        }
        return converters.courseToCourseView(course);
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        return converters.coursesToCourseViews(courseDao.findByNameContains(courseName));
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        return converters.coursesToCourseViews(courseDao.findByDateBefore(end));
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        return converters.coursesToCourseViews(courseDao.findByDateAfter(start));
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        Student student = studentDao.findById(studentId);
        return course.enrollStudent(student);
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        Student student = studentDao.findById(studentId);
        return course.unrollStudent(student);
    }

    @Override
    public CourseView findById(int id) {
        return converters.courseToCourseView(courseDao.findById(id));
    }

    @Override
    public List<CourseView> findAll() {
        return converters.coursesToCourseViews(courseDao.findAll());
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        return converters.coursesToCourseViews(courseDao.findByStudentId(studentId));
    }

    @Override
    public boolean deleteCourse(int id) {
        Course course = courseDao.findById(id);
        if(course != null){
            course.setStudents(null);
        }
        return courseDao.removeCourse(course);
    }
}
