package controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entity.*;

@Transactional
@Controller
@RequestMapping("/Customer/")
public class CustomerController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("Index")
	public String Index(ModelMap model){
		Session session = factory.getCurrentSession();
		String hql = "FROM Customer";
		Query query = session.createQuery(hql);
		List<Customer> list = query.list();
		model.addAttribute("Customers",list);
		return "Customer/CustomerManagement";
	}
	
	@RequestMapping(value="OutGoing/{Id}")
	public String OutGoingHistory(ModelMap model, @PathVariable int Id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM OutGoing o WHERE o.customer.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Id);
		List<OutGoing> list = query.list();
		model.addAttribute("Outgoing", list);
		return "Customer/HistoryCustomer";
	}
	
	@RequestMapping(value="Insert", method=RequestMethod.GET)
	public String Insert(ModelMap model){
		model.addAttribute("customer", new Customer());
		return "Customer/InsertCustomer";
	}
	
	@RequestMapping(value="Insert", method=RequestMethod.POST)
	public String Insert(ModelMap model, @Validated @ModelAttribute("customer") Customer customer, BindingResult errors){
		if(customer.getName().trim().length()==0)
			errors.rejectValue("name", "customer", "T??n Kh??ch H??ng kh??ng ???????c ????? tr???ng!");
		
		if(customer.getEmail().trim().length()==0)
			errors.rejectValue("email", "customer", "Email kh??ng ???????c ????? tr???ng!");
		
		if(customer.getPhoneNumber().trim().length()==0 || !validationPhoneNumber(customer.getPhoneNumber()))
			errors.rejectValue("phoneNumber", "customer", "S??? ??i??n Tho???i kh??ng h???p l???!");
		
		if(customer.getName().trim().length()==0)
			errors.rejectValue("address", "customer", "?????a Ch??? kh??ng ???????c ????? tr???ng!");
		
		
		if(errors.hasErrors())
			return "Customer/InsertCustomer";
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(customer);
			t.commit();			
			return "redirect:/Customer/Index.htm";
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Th??m m???i th???t b???i");		
		}
		finally{
			session.close();
		}		
		return "Customer/InsertCustomer";
	}
	
	@RequestMapping(value="Delete/{id}", method=RequestMethod.GET)
	public String Delete(ModelMap model, @PathVariable int id, RedirectAttributes redirect){
		Session session = factory.openSession();
		String hql = "FROM Customer WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Customer cutomer = (Customer) query.uniqueResult();
		Transaction t = session.beginTransaction();
		try {
			session.delete(cutomer);
			t.commit();
			redirect.addFlashAttribute("message","X??a Th??nh C??ng");
		} catch (Exception e) {
			t.rollback();
			redirect.addFlashAttribute("message", "X??a Th???t B???i Kh??ch H??ng ???? ?????t H??ng");
		} finally{
			session.close();
		}
		return "redirect:/Customer/Index.htm";
	}
	@RequestMapping(value="Update/{id}",method=RequestMethod.GET)
	public String update(ModelMap model, @PathVariable int id){
		Session session = factory.getCurrentSession();
		String hql = "FROM Customer WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Customer customer = (Customer) query.uniqueResult();
		model.addAttribute("customer", customer);
		
		return "Customer/UpdateCustomer";
	}
	
	@RequestMapping(value="Update/{id}", method=RequestMethod.POST)
	public String update(ModelMap model, @PathVariable int id, @ModelAttribute("customer") Customer customer, BindingResult errors){
		if (customer.getName().trim().length() == 0)
			errors.rejectValue("name", "supplier", "T??n Nh?? Cung C???p kh??ng ???????c ????? tr???ng!");

		if (customer.getEmail().trim().length() == 0)
			errors.rejectValue("email", "supplier", "Email kh??ng ???????c ????? tr???ng!");

		if (customer.getPhoneNumber().trim().length() == 0|| !validationPhoneNumber(customer.getPhoneNumber()))
			errors.rejectValue("phoneNumber", "supplier", "S??? ??i??n Tho???i kh??ng h???p l???!");

		if (customer.getName().trim().length() == 0)
			errors.rejectValue("address", "supplier", "?????a Ch??? kh??ng ???????c ????? tr???ng!");

		if (errors.hasErrors())
			return "Customer/UpdateCustomer";
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(customer);
			t.commit();
			model.addAttribute("message", "Ch???nh s???a th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Ch???nh s???a th???t b???i");
			return "Customer/UpdateCustomer";
		}
		finally {
			session.close();
		}
		
		return "Customer/UpdateCustomer";
	}
	
	public boolean validationPhoneNumber(String phone) {
		String regex="^\\d{10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phone);
		if(matcher.find()) {
			return true;
		}
		return false;
	}
}
