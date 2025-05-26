package com.bridgelab.lms.repository;

import com.bridgelab.lms.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InstructorRepository extends JpaRepository<Instructor,Long> {


}
