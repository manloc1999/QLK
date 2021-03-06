 package controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.hibernate.*;
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
@RequestMapping("/Supplier/")
public class SupplierController {
	@Autowired
	SessionFactory factory;
	
	Supplier sp;
	int ID;
	@RequestMapping("Index")
	public String Index(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Supplier";
		Query query = session.createQuery(hql);
		List<Supplier> list = query.list();
		model.addAttribute("Suppliers", list);
		return "Supplier/SupplierManagement";
	}
	
	@RequestMapping(value="InComing/{Id}")
	public String InComingHistory(ModelMap model, @PathVariable int Id) {
		ID = Id;
		Session session = factory.getCurrentSession();
		String hql = "FROM InComing i WHERE i.product.supplier.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Id);
		List<InComing> list = query.list();
		model.addAttribute("Incoming", list);		
		return "Supplier/HistorySupplier";
	}
	
	@RequestMapping(value="OutGoing/{Id}")
	public String OutGoingHistory(ModelMap model, @PathVariable int Id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM OutGoing o WHERE o.incoming.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Id);
		List<OutGoing> list = query.list();	
		model.addAttribute("Outgoing", list);
		model.addAttribute("id",ID);
		return "Supplier/HistoryOutGoing";	
	}

	@RequestMapping(value = "Insert", method = RequestMethod.GET)
	public String Insert(ModelMap model) {
		model.addAttribute("supplier", new Supplier());
		return "Supplier/InsertSupplier";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.POST)
	public String Insert(ModelMap model, @Validated @ModelAttribute("supplier") Supplier supplier,
			BindingResult errors) {
		if (supplier.getName().trim().length() == 0)
			errors.rejectValue("name", "supplier", "T??n Nh?? Cung C???p kh??ng ???????c ????? tr???ng!");

		if (supplier.getEmail().trim().length() == 0)
			errors.rejectValue("email", "supplier", "Email kh??ng ???????c ????? tr???ng!");

		if (supplier.getPhoneNumber().trim().length() == 0 || !validationPhoneNumber(supplier.getPhoneNumber()))
			errors.rejectValue("phoneNumber", "supplier", "S??? ??i??n Tho???i kh??ng h???p l???!");

		if (supplier.getName().trim().length() == 0)
			errors.rejectValue("address", "supplier", "?????a Ch??? kh??ng ???????c ????? tr???ng!");

		if (errors.hasErrors())
			return "Supplier/InsertSupplier";

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(supplier);
			t.commit();
			model.addAttribute("message", "Th??m m???i th??nh c??ng");
			return "redirect:/Supplier/Index.htm";
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Th??m m???i th???t b???i");
		} finally {
			session.close();
		}
		return "Supplier/InsertSupplier";
	}
	
	@RequestMapping(value="Delete/{id}", method=RequestMethod.GET)
	public String Delete(ModelMap model, @PathVariable int id, RedirectAttributes redirect){
		Session session = factory.openSession();
		String hql = "FROM Supplier WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Supplier supplier = (Supplier) query.uniqueResult();
		Transaction t = session.beginTransaction();
		try {
			session.delete(supplier);
			t.commit();
			redirect.addFlashAttribute("message","X??a Th??nh C??ng");
		} catch (Exception e) {
			t.rollback();
			redirect.addFlashAttribute("message", "X??a Th???t B???i Nh?? Cung C???p ???? Th??m S???n Ph???m Nh???p Kho");
		} finally{
			session.close();
		}
		return "redirect:/Supplier/Index.htm";
	}
	
	@RequestMapping(value="Update/{id}",method=RequestMethod.GET)
	public String update(ModelMap model, @PathVariable int id){
		Session session = factory.getCurrentSession();
		String hql = "FROM Supplier WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Supplier supplier = (Supplier) query.uniqueResult();
		model.addAttribute("supplier", supplier);
		
		return "Supplier/UpdateSupplier";
	}
	
	@RequestMapping(value="Update/{id}", method=RequestMethod.POST)
	public String update(ModelMap model, @PathVariable int id, @ModelAttribute("supplier") Supplier supplier, BindingResult errors){
		if (supplier.getName().trim().length() == 0)
			errors.rejectValue("name", "supplier", "T??n Nh?? Cung C???p kh??ng ???????c ????? tr???ng!");

		if (supplier.getEmail().trim().length() == 0)
			errors.rejectValue("email", "supplier", "Email kh??ng ???????c ????? tr???ng!");

		if (supplier.getPhoneNumber().trim().length() == 0|| !validationPhoneNumber(supplier.getPhoneNumber()))
			errors.rejectValue("phoneNumber", "supplier", "S??? ??i??n Tho???i kh??ng h???p l???!");

		if (supplier.getName().trim().length() == 0)
			errors.rejectValue("address", "supplier", "?????a Ch??? kh??ng ???????c ????? tr???ng!");

		if (errors.hasErrors())
			return "Supplier/UpdateSupplier";
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(supplier);
			t.commit();
			model.addAttribute("message", "Ch???nh s???a th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Ch???nh s???a th???t b???i");
			return "Supplier/UpdateSupplier";
		}
		finally {
			session.close();
		}
		
		return "Supplier/UpdateSupplier";
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
