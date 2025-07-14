package com.Spring.SpringBootWeb.Repository;

import com.Spring.SpringBootWeb.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface repo extends JpaRepository<Student,Integer> {

    @Query("select s from Student s")
    List<Student> StudentList();

    @Query("select s from Student s where s.rollNo=?1")
    Student getByRollNo(int rollNo);

    @Transactional
    @Modifying
    @Query("delete from Student s where s.rollNo=?1")
    void deleteByRollNo(int rollNo);

    @Transactional
    @Modifying
    @Query(value="insert into student (roll_no,name,standard,marks) values(?1,?2,?3,?4)",
            nativeQuery = true)
    void addStudent(int rollNo,String name,String standard,int marks);
}
