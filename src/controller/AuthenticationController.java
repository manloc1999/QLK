package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import entity.*;

@Transactional
@Controller
@RequestMapping("/account/")
public class AuthenticationController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String Login(ModelMap model){	
		model.addAttribute("admin",	 new Admin());
		return "authentication/login";
	}
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String Login(ModelMap model, HttpSession session,@ModelAttribute("admin") Admin admin, BindingResult errors){	
		if(admin.getUsername().trim().length() == 0)
			errors.rejectValue("username", "admin", "Tên đăng nhập không được bỏ trống!");
		if(admin.getPassword().trim().length() == 0)
			errors.rejectValue("password", "admin", "Mật khẩu không được bỏ trống!");
		if(errors.hasErrors())
			return "authentication/login";
		
		Admin a = FindUser(admin.getUsername());	
		
		
		if (a!= null) {
			if (a.getUsername().equals(admin.getUsername()) && a.getPassword().equals(admin.getPassword())) {
				session.setAttribute("mAdmin", a);
	
				return "redirect:/InComing/Index.htm";
			}	
		}
		model.addAttribute("error", "Sai thông tin đăng nhập!");
		
		return "authentication/login";
	}
	
	
	public Admin FindUser(String UserName){
		Session session = factory.getCurrentSession();
		String hql = "FROM Admin a WHERE a.username = '"+ UserName + "'";
		Query query = session.createQuery(hql);
		Admin a  = (Admin) query.uniqueResult();
		return a;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		session.removeAttribute("mAdmin");
		
		return "redirect:/Home/Index.htm";
	}
	
	
	@RequestMapping("forgotpassword")
	public String RecoverPassword()
	{
		return "authentication/forgotpassword";
	}
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String Register(ModelMap model)
	{
		model.addAttribute("admin", new Admin());
		return "authentication/register";
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String Register(ModelMap model, @Validated @ModelAttribute("admin") Admin admin, BindingResult errors,@RequestParam("passwordconfirm") String passwordconfirm)
	{
		if(admin.getFirstname().trim().length()==0)
			errors.rejectValue("firstname", "admin", "Tên Không được để trống !");
		
		if(admin.getAddress().trim().length()==0)
			errors.rejectValue("address", "admin", "Địa Chỉ Không được để trống !");
		
		if(admin.getPassword().trim().length()==0)
			errors.rejectValue("firstname", "admin", "Tên Admin Không được để trống !");
		
		if(admin.getUsername().trim().length()==0)
			errors.rejectValue("firstname", "admin", "Tên Admin Không được để trống !");
		
		if(admin.getLastname().trim().length()==0)
			errors.rejectValue("firstname", "admin", "Tên Admin Không được để trống !");
		
		if(admin.getEmail().trim().length()==0)
			errors.rejectValue("firstname", "admin", "Tên Admin Không được để trống !");
		
		if (errors.hasErrors())
			return "authentication/register";
		
		
		if(!passwordconfirm.equals(admin.getPassword())){
			model.addAttribute("message", "Tạo tài khoản thất bại do Xác nhận mật khẩu sai");
			return "authentication/register";
		}							
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(admin);
			t.commit();
			model.addAttribute("message", "Tạo tài khoản thành công");
			return "redirect:/account/login.htm";
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Tạo tài khoản thất bại");
		} finally {
			session.close();
		}
		
		return "authentication/register";
	}
}
