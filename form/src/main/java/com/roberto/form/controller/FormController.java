package com.roberto.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FormController {

  @GetMapping("/form")
  public ModelAndView getList(){
    ModelAndView mV = new ModelAndView("form");
    return mV;
  }
  
}
