package com.Spring.SpringBootWeb.Service;

import com.Spring.SpringBootWeb.Model.Student;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvGenerator {
    private static final String CSV_HEADER = "RollNo, Name, Standard, Marks \n";

    public byte[] generateCsv(List<Student> students) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (Student student : students) {
            csvContent.append(student.getRollNo()).append(",")
                    .append(student.getName()).append(",")
                    .append(student.getStandard()).append(",")
                    .append(student.getMarks()).append("\n");

        }

        return csvContent.toString().getBytes(StandardCharsets.UTF_8);
    }
}
