package com.heiketu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/serverError")
    public String serverError(){
        return "500";
    }

    @RequestMapping("/pageNotFind")
    public String pageNotFind(){
        return "404";
    }

}
