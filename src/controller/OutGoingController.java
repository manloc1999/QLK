package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
@RequestMapping("/OutGoing/")
public class OutGoingController {
	@Autowired
	SessionFactory factory;

	int cateId;
	OutGoing out;

	@RequestMapping("Index")
	public String Index(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM OutGoing";
		Query query = session.createQuery(hql);
		List<OutGoing> list = query.list();
		model.addAttribute("Outgoing", list);
		return "OutGoing/OutGoing";
	}

	@ModelAttribute("incoming")
	public List<InComing> getProduct() {
		Session session = factory.getCurrentSession();
		String hql = "FROM InComing i WHERE i.product.category.id =:id AND i.amount > 0";
		Query query = session.createQuery(hql);
		query.setParameter("id", cateId);
		List<InComing> list = query.list();

		return list;
	}

	@ModelAttribute("incomings")
	public List<InComing> getProduct1() {
		Session session = factory.getCurrentSession();
		String hql = "FROM InComing i WHERE i.amount > 0";
		Query query = session.createQuery(hql);
		List<InComing> list = query.list();
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

	@ModelAttribute("customer")
	public List<Customer> GetCustomer() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Customer";
		Query query = session.createQuery(hql);
		List<Customer> list = query.list();
		return list;
	}

	@ModelAttribute("supplier")
	public List<Supplier> getSupplier() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Supplier";
		Query query = session.createQuery(hql);
		List<Supplier> list = query.list();
		return list;
	}

	@RequestMapping(value = "Category", method = RequestMethod.GET)
	public String getCate(ModelMap model) {
		model.addAttribute("category1", new Category());
		return "OutGoing/Category";
	}

	@RequestMapping(value = "Category", method = RequestMethod.POST)
	public String getCate(ModelMap model, @ModelAttribute("category1") Category category) {
		cateId = category.getId();
		return "redirect:/OutGoing/Insert.htm";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.GET)
	public String Insert(ModelMap model) {
		model.addAttribute("outgoing", new OutGoing());
		return "OutGoing/InsertOutGoing";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.POST)
	public String Insert(ModelMap model, @Validated @ModelAttribute("outgoing") OutGoing outgoing,
			BindingResult errors) {

		if (outgoing.getAmount() == 0) {
			errors.rejectValue("amount", "outgoing", "S??? l?????ng kh??ng ???????c ????? tr???ng!");
		}

		if (errors.hasErrors())
			return "OutGoing/InsertOutGoing";

		if (outgoing.getIncoming() == null) {
			model.addAttribute("message", "S???n ph???m n??y kh??ng c?? trong kho");
			return "OutGoing/InsertOutGoing";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(outgoing.getIncoming().getId());

		outgoing.setDateOut(java.util.Calendar.getInstance().getTime());

		outgoing.setDate(dateFormat.format(date));

		InComing temp = GetinComing(outgoing.getIncoming().getId());

		if (outgoing.getAmount() > temp.getAmount()) {
			model.addAttribute("message",
					"Th??m m???i th???t b???i S??? l?????ng trong kho kh??ng ????? h??ng ch??? c??n" + temp.getAmount());
			return "OutGoing/InsertOutGoing";
		}

		if (outgoing.getAmount() == temp.getAmount()) {
			outgoing.setTotal((outgoing.getFreightCost() + outgoing.getAmount() * temp.getPriceEach())
					- (outgoing.getDiscount() / 100) * (outgoing.getAmount() * temp.getPriceEach()));
			outgoing.setStatus("???? Xu???t H??ng");
			temp.setStatus("???? Xu???t H???t");
			temp.setAmount(0);
			temp.setPrice(0);
			temp.setOut(outgoing.getAmount());
		} else if (outgoing.getAmount() < temp.getAmount()) {
			outgoing.setTotal((outgoing.getFreightCost() + outgoing.getAmount() * temp.getPriceEach())
					- (outgoing.getDiscount() / 100) * (outgoing.getAmount() * temp.getPriceEach()));
			outgoing.setStatus("???? Xu???t H??ng");
			temp.setAmount(temp.getAmount() - outgoing.getAmount());
			temp.setPrice(temp.getPriceEach() * temp.getAmount());
			temp.setOut(temp.getOut() + outgoing.getAmount());
			temp.setStatus("???? Xu???t" + " " + temp.getOut() + " C??n L???i " + temp.getAmount());
		}

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(outgoing);
			t.commit();
			model.addAttribute("message", "Th??m m???i th??nh c??ng");
			return "redirect:/OutGoing/Index.htm";

		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Th??m m???i th???t b???i");

		} finally {
			session.close();
		}
		return "OutGoing/InsertOutGoing";
	}

	public InComing GetinComing(int id) {
		InComing temp;
		Session session1 = factory.getCurrentSession();
		String hql = "FROM InComing i Where i.id =:id";
		Query query = session1.createQuery(hql);
		query.setParameter("id", id);
		temp = (InComing) query.uniqueResult();
		return temp;
	}

	@RequestMapping(value = "Delete/{id}", method = RequestMethod.GET)
	public String Delete(ModelMap model, @PathVariable int id, RedirectAttributes redirect) {
		Session session = factory.openSession();
		String hql = "FROM OutGoing WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		OutGoing ougoing = (OutGoing) query.uniqueResult();
		Transaction t = session.beginTransaction();
		try {
			session.delete(ougoing);
			t.commit();
			redirect.addFlashAttribute("message", "X??a Th??nh C??ng");
		} catch (Exception e) {
			t.rollback();
			redirect.addFlashAttribute("message", "X??a Th???t B???i");
		} finally {
			session.close();
		}
		return "redirect:/OutGoing/Index.htm";
	}

	@RequestMapping(value = "Update/{id}", method = RequestMethod.GET)
	public String update(ModelMap model, @PathVariable int id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM OutGoing WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		OutGoing outgoing = (OutGoing) query.uniqueResult();
		model.addAttribute("outgoing", outgoing);
		out = outgoing;
		return "OutGoing/UpdateOutGoing";
	}

	@RequestMapping(value = "Update/{id}", method = RequestMethod.POST)
	public String update(ModelMap model, @PathVariable int id, @ModelAttribute("outgoing") OutGoing outgoing,
			BindingResult errors) {

		if (outgoing.getAmount() == 0) {
			errors.rejectValue("amount", "outgoing", "S??? l?????ng kh??ng ???????c ????? tr???ng!");
		}

		if (errors.hasErrors())
			return "OutGoing/InsertOutGoing";

		if (outgoing.getIncoming() == null) {
			model.addAttribute("message", "S???n ph???m n??y kh??ng c?? trong kho");
			return "OutGoing/InsertOutGoing";
		}
		InComing intemp = GetinComing(outgoing.getIncoming().getId());
		

		if (outgoing.getIncoming().getId() == out.getIncoming().getId()) {		
			if (outgoing.getAmount() > (intemp.getAmount()+out.getAmount())) {
				model.addAttribute("message", "1 S??? L?????ng Trong kho kh??ng ????? ch??? c??n " + (intemp.getAmount()+out.getAmount()));
				return "OutGoing/UpdateOutGoing";
			}
			intemp.setAmount(out.getAmount()+intemp.getAmount());
			
			if(out.getAmount() > outgoing.getAmount()){
				intemp.setOut(intemp.getOut() - outgoing.getAmount());	
			}
			else if(out.getAmount() < outgoing.getAmount()){
				intemp.setOut(intemp.getOut() - out.getAmount());	
				intemp.setOut(intemp.getOut() + outgoing.getAmount());	
			}
			intemp.setAmount(intemp.getAmount()- outgoing.getAmount());	
			intemp.setStatus("???? Xu???t " + intemp.getOut() + " C??n L???i " + intemp.getAmount());			
			intemp.setPrice(intemp.getPriceEach()*intemp.getAmount());
		}
		else{
			InComing intemp2 = GetinComing(out.getIncoming().getId());
			intemp2.setAmount(intemp2.getAmount()+out.getAmount());
			intemp2.setOut(intemp2.getOut() - out.getAmount());
			intemp2.setStatus("???? Xu???t " + intemp2.getOut() + " C??n L???i " + intemp2.getAmount());	
			intemp2.setPrice(intemp2.getPrice()*intemp2.getAmount());
			
			if (outgoing.getAmount() > intemp.getAmount()) {
				model.addAttribute("message", "S??? L?????ng Trong kho kh??ng ????? ch??? c??n " + intemp.getAmount());
				return "OutGoing/UpdateOutGoing";
			}			
			intemp.setOut(intemp.getOut() + outgoing.getAmount());
			intemp.setAmount(intemp.getAmount()-out.getAmount());
			intemp.setPrice(intemp.getPriceEach()*intemp.getAmount());		
			intemp.setStatus("???? Xu???t "+ intemp.getOut() + " C??n L???i " + intemp.getAmount() );
		}

		outgoing.setTotal(outgoing.getFreightCost()+outgoing.getAmount()*intemp.getPriceEach()-(outgoing.getDiscount() / 100) * (outgoing.getAmount() * intemp.getPriceEach()));
		outgoing.setStatus("???? Xu???t H??ng");
		outgoing.setDateOut(out.getDateOut());
		outgoing.setDate(out.getDate());
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(outgoing);
			t.commit();
			model.addAttribute("message", "Ch???nh s???a th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Ch???nh s???a th???t b???i");
			return "OutGoing/UpdateOutGoing";
		} finally {
			session.close();
		}
		out = null;
		return "OutGoing/UpdateOutGoing";
	}

}
