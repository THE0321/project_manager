package com.pm.controller;

import com.pm.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController extends com.pm.util.Controller {
    @GetMapping(value = {"/", ""})
    public String index(HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        Long loginIdx = super.getLoginData(request).getIdx();
        Long projectIdx = super.getProjectData(request);

        return "index";
    }
}
