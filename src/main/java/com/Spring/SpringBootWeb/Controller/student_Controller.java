package com.Spring.SpringBootWeb.Controller;

import com.Spring.SpringBootWeb.Model.Student;
import com.Spring.SpringBootWeb.Repository.repo;
import com.Spring.SpringBootWeb.Service.CsvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class student_Controller {

    @Autowired
    private repo repo1;

    @Autowired
    private CsvGenerator csvGenerator;

    @RequestMapping("/")
    public String home()
    {
        return "index";
    }

    @RequestMapping("/add")
    public String add()
    {
        return "addStudent";
    }

    @PostMapping("/added")
    public String display(@ModelAttribute Student s1, Model model) {

        if(repo1.existsById(s1.getRollNo()))
        {
            model.addAttribute("message","Roll number already exists");
            return "error";
        }
        else {
            repo1.addStudent(s1.getRollNo(),s1.getName(),s1.getStandard(),s1.getMarks());
            model.addAttribute("s1", s1);
            return "addResult";
        }

    }

    @GetMapping("/record")
    public String record(Model model)
    {
        List<Student> studentList=new ArrayList<>();
        studentList=repo1.StudentList();
        model.addAttribute("students" ,studentList);
        return "studentList";

    }

    @GetMapping("/delete")
    public String deleteList(Model model)
    {
        List<Student> deleteList=new ArrayList<>();
        deleteList=repo1.StudentList();
        model.addAttribute("deleteList",deleteList);
        return "deleteList";
    }

    @PostMapping("/delete/{id}")
    public String deleteUpdated(@PathVariable int id, Model model)
    {
        repo1.deleteByRollNo(id);
        List<Student> deleteUpdated=new ArrayList<>();
        deleteUpdated=repo1.StudentList();
        model.addAttribute("deleteList",deleteUpdated);

        return "deleteList";
    }

    @PostMapping("/update")
    public String updateList(Model model)
    {
        List<Student> Updated=new ArrayList<>();
        Updated=repo1.StudentList();
        model.addAttribute("Updated",Updated);

        return "updateStudents";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(Model model,@PathVariable int id)
    {
        Student s1=repo1.getByRollNo(id);
        model.addAttribute("Std",s1);
        return "updateStudent";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> generateCsvFile() {
        List<Student> students = repo1.StudentList();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "students.csv");
        //xlsx xls
        byte[] csvBytes = csvGenerator.generateCsv(students);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
