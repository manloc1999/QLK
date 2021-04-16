package controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Home/")
public class HomeController {
	
	@RequestMapping("Index")
	public String Index(ModelMap model){
	
		return "Home/Home";
	}
}
