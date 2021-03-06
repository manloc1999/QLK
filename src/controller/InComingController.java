package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entity.*;

@Transactional
@Controller
@RequestMapping("/InComing/")
public class InComingController {
	@Autowired
	SessionFactory factory;

	int cateId;
	
	InComing in;
	@RequestMapping("Index")
	public String Index(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM InComing";
		Query query = session.createQuery(hql);
		List<InComing> list = query.list();
		model.addAttribute("Incoming", list);
		return "InComing/InComing";
	}

	@ModelAttribute("supplier")
	public List<Supplier> getSupplier() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Supplier";
		Query query = session.createQuery(hql);
		List<Supplier> list = query.list();
		return list;
	}

	@ModelAttribute("category")
	public List<Category> getCategory() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		return list;
	}

	@ModelAttribute("product")
	public List<Product> getProductOfCatogory() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Product p Where p.category.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", cateId);
		List<Product> list = query.list();
		return list;
	}
	
	@ModelAttribute("products")
	public List<Product> getProductOfCatogorys() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Product p";
		Query query = session.createQuery(hql);
		List<Product> list = query.list();
		return list;
	}

	@RequestMapping(value = "Category", method = RequestMethod.GET)
	public String getCate(ModelMap model) {
		model.addAttribute("category1", new Category());
		return "InComing/Category";
	}

	@RequestMapping(value = "Category", method = RequestMethod.POST)
	public String getCate(ModelMap model, @ModelAttribute("category1") Category category) {
		cateId = category.getId();
		return "redirect:/InComing/Insert.htm";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.GET)
	public String Insert(ModelMap model) {
		model.addAttribute("incoming", new InComing());
		return "InComing/InsertInComing";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.POST)
	public String Insert(ModelMap model, @Validated @ModelAttribute("incoming") InComing incoming,
			BindingResult errors) throws ParseException {
		if (incoming.getAmount() == 0)
			errors.rejectValue("amount", "incoming", "S??? l?????ng kh??ng ???????c b??? tr???ng!");

		if (errors.hasErrors())
			return "InComing/InsertInComing";

		SimpleDateFormat  dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.getDateInstance());
		
		System.out.println(dateFormat.format(date));
		
		incoming.setAmount(incoming.getAmount());
		
		
		Product temp = getProductName(incoming.getProduct().getId());
		incoming.setStatus("???? Nh???p H??ng");
		incoming.setDateIn(dateFormat.parse(dateFormat.format(date)));
		incoming.setAlso(incoming.getAmount());
		
		incoming.setDate(dateFormat.format(date));
	
		incoming.setName(temp.getName() + " Ng??y Nh???p " + dateFormat.format(date));
		incoming.setPrice(temp.getSellingPrice() * incoming.getAmount());
		incoming.setPriceEach(incoming.getPrice() / incoming.getAmount());

		System.out.println(incoming.getDateIn());

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(incoming);
			t.commit();
			model.addAttribute("message", "Th??m m???i th??nh c??ng");
			return "redirect:/InComing/Index.htm";
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Th??m m???i th???t b???i");

		} finally {
			session.close();
		}
		return "InComing/InsertInComing";
	}

	public Product getProductName(int id) {
		Product temp;
		Session session = factory.getCurrentSession();
		String hql = "FROM Product p Where p.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		temp = (Product) query.uniqueResult();
		return temp;
	}
	
	@RequestMapping(value="Delete/{id}", method=RequestMethod.GET)
	public String Delete(ModelMap model, @PathVariable int id, RedirectAttributes redirect){
		Session session = factory.openSession();
		String hql = "FROM InComing WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		InComing incoming = (InComing) query.uniqueResult();
		Transaction t = session.beginTransaction();
		try {
			session.delete(incoming);
			t.commit();
			redirect.addFlashAttribute("message","X??a Th??nh C??ng");
		} catch (Exception e) {
			t.rollback();
			redirect.addFlashAttribute("message", "X??a Th???t B???i Do M???t H??ng C?? S???n Ph???m ???? Xu???t H??ng");
		} finally{
			session.close();
		}
		return "redirect:/InComing/Index.htm";
	}
	@RequestMapping(value = "Update/{id}", method = RequestMethod.GET)
	public String update(ModelMap model, @PathVariable int id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM InComing WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		InComing incoming = (InComing) query.uniqueResult();
		model.addAttribute("incoming", incoming);
		in = incoming;
		return "InComing/UpdateInComing";
	}

	@RequestMapping(value = "Update/{id}", method = RequestMethod.POST)
	public String update(ModelMap model, @PathVariable int id, @ModelAttribute("incoming") InComing incoming,
			BindingResult errors) {

		if (incoming.getAmount() == 0) {
			errors.rejectValue("amount", "outgoing", "S??? l?????ng kh??ng ???????c ????? tr???ng!");
		}

		if (errors.hasErrors())
			return "InComing/UpdateInComing";
		
		if(incoming.getProduct().getId() != in.getProduct().getId()){			
			if(in.getOut()!=0){
				model.addAttribute("message", "S???n ph???m ???? ???????c xu???t h??ng kh??ng thay ?????i t??n ???????c");
				return "InComing/UpdateInComing";
			}
		}		

		incoming.setDateIn(in.getDateIn());
		incoming.setDate(in.getDate());		
		Product temp = getProductName(incoming.getProduct().getId());
		incoming.setStatus("???? Nh???p H??ng");	
		incoming.setName(temp.getName() + " Ng??y Nh???p " + incoming.getDate());
		incoming.setPrice(temp.getSellingPrice() * incoming.getAmount());
		incoming.setPriceEach(incoming.getPrice() / incoming.getAmount());
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(incoming);
			t.commit();
			model.addAttribute("message", "Ch???nh s???a th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Ch???nh s???a th???t b???i");
			return "InComing/UpdateInComing";
		} finally {
			session.close();
		}
		in = null;
		return "InComing/UpdateInComing";
	}
	
}
