package kitos.cardwatcher.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController 
{
	
	
    @GetMapping("/")
    public String helloPage() {
    	return "general/hello";// maps to src/main/resources/templates/hello.html
    }
	
}
