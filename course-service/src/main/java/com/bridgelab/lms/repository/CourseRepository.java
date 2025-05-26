package com.bridgelab.lms.repository;

import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c.title, c.description FROM Course c WHERE c.isActive = true")
    List<Course> findByIsActiveTrue();

    List<Course> findByInstructorId(Long instructorId);

}
