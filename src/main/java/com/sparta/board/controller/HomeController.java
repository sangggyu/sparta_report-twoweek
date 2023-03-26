package com.sparta.board.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class HomeController {


    @GetMapping("/health")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

}

