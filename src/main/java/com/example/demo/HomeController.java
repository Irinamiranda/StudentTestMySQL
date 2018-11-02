package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TestRepository testRepository;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "index";
    }

    @GetMapping("/addstudent")
    public String addStudent(Model model) {
        Student student = new Student();

        model.addAttribute("student", student);
        return "addstudent";
    }

    @PostMapping("/processStudent")
    public String processStudent(@Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "addstudent";
        }
        studentRepository.save(student);

        return "redirect:/";
    }

    @GetMapping("/addtest")
    public String addTest(Model model, @RequestParam("id") Long studentId) {
        System.out.println("studentId = " + studentId);
        Test test = new Test();
        test.setStudent(studentRepository.findById(studentId).get());

        model.addAttribute("test", test);
        return "addtest";
    }

    @PostMapping("/processTest")
    public String processTest(@Valid Test test, BindingResult result) {
        if (result.hasErrors()) {
            return "addtest";
        }
        testRepository.save(test);

        return "redirect:/";
    }
}
