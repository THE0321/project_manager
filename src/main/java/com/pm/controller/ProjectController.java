package com.pm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @GetMapping(value = {"/", ""})
    public String list() {
        return "/project/list";
    }
}
