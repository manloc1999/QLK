package controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entity.*;

@Transactional
@Controller
@RequestMapping("/Product/")
public class ProductController {
	@Autowired
	SessionFactory factory;

	@Autowired
	ServletContext context;
	
	Product p;
	int ID;

	@RequestMapping("Index")
	public String Index(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Product";
		Query query = session.createQuery(hql);
		List<Product> list = query.list();
		model.addAttribute("Products", list);
		return "Product/ProductManagement";
	}
	
	@RequestMapping(value="InComing/{Id}")
	public String InComingHistory(ModelMap model, @PathVariable int Id) {
		ID = Id;
		Session session = factory.getCurrentSession();
		String hql = "FROM InComing i WHERE i.product.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Id);
		List<InComing> list = query.list();
		model.addAttribute("Incoming", list);		
		return "Product/HistoryProduct";
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
		return "Product/HistoryOutGoing";	
	}
	

	@ModelAttribute("category")
	public List<Category> getCategory() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
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

	@RequestMapping(value = "SeePhoto/{Id}")
	public String SeePhoto(ModelMap model, @PathVariable int Id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Product p Where p.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Id);
		Product p = (Product)query.uniqueResult();
		model.addAttribute("product", p);
		return "Product/photo";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.GET)
	public String Insert(ModelMap model) {
		model.addAttribute("product", new Product());
		return "Product/InsertProduct";
	}

	@RequestMapping(value = "Insert", method = RequestMethod.POST)
	public String Insert(ModelMap model, @Validated @ModelAttribute("product") Product product, BindingResult errors,
			@RequestParam("attachment") MultipartFile photo) {
		if (product.getName().trim().length() == 0)
			errors.rejectValue("name", "product", "T??n s???n ph???m kh??ng ???????c b??? tr???ng!");

		if (product.getSellingPrice() == 0)
			errors.rejectValue("sellingPrice", "product", "Gi?? b??n kh??ng ???????c b??? tr???ng!");

		if (errors.hasErrors())
			return "Product/InsertProduct";

		if (photo.isEmpty()) {
			model.addAttribute("msg", "<div class=\"alert alert-danger\" role=\"alert\">\r\n"
					+ "					  Vui l??ng ch???n file ???nh!\r\n" + "					</div>");
			return "Product/InsertProduct";
		}

		try {
			String path = context.getRealPath("/assetss/image/" + photo.getOriginalFilename());
			photo.transferTo(new File(path));
		} catch (Exception e) {
			model.addAttribute("message", "<div class=\"alert alert-danger\" role=\"alert\">\r\n"
					+ "					  L???i upload file ???nh!\r\n" + "					</div>");
			return "Product/InsertProduct";
		}

		product.setPhoto(photo.getOriginalFilename());

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(product);
			t.commit();
			model.addAttribute("message", "Th??m m???i th??nh c??ng");
			return "redirect:/Product/Index.htm";
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Th??m m???i th???t b???i");
		} finally {
			session.close();
		}
		return "Product/InsertProduct";
	}

	@RequestMapping(value = "Delete/{id}", method = RequestMethod.GET)
	public String Delete(ModelMap model, @PathVariable int id, RedirectAttributes redirect) {
		Session session = factory.openSession();
		String hql = "FROM Product WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Product product = (Product) query.uniqueResult();
		Transaction t = session.beginTransaction();
		try {
			session.delete(product);
			t.commit();
			redirect.addFlashAttribute("message", "X??a Th??nh C??ng");

		} catch (Exception e) {
			t.rollback();
			redirect.addFlashAttribute("message", "X??a Th???t B???i Do S???n Ph???m ???? Nh???p Kho");
		} finally {
			session.close();
		}
		return "redirect:/Product/Index.htm";
	}
	@RequestMapping(value = "Update/{id}", method = RequestMethod.GET)
	public String Update(ModelMap model, @PathVariable int id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Product WHERE id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Product product = (Product) query.uniqueResult();
		p = product;
		model.addAttribute("product", product);
		return "Product/UpdateProduct";
	}

	@RequestMapping(value = "Update/{id}", method = RequestMethod.POST)
	public String Update(ModelMap model, @PathVariable int id, @Validated @ModelAttribute("product") Product product, BindingResult errors,	@RequestParam("attachment") MultipartFile photo) {
		if (product.getName().trim().length() == 0)
			errors.rejectValue("name", "product", "T??n s???n ph???m kh??ng ???????c b??? tr???ng!");

		if (product.getSellingPrice() == 0)
			errors.rejectValue("sellingPrice", "product", "Gi?? b??n kh??ng ???????c b??? tr???ng!");

		if (errors.hasErrors())
			return "Product/UpdateProduct";
		

		
		if (photo.isEmpty()) {
			product.setPhoto(p.getPhoto());
		}
		else{
			try {
				String path = context.getRealPath("/assetss/image/" + photo.getOriginalFilename());
				photo.transferTo(new File(path));
				product.setPhoto(photo.getOriginalFilename());
			} catch (Exception e) {
				model.addAttribute("message", "<div class=\"alert alert-danger\" role=\"alert\">\r\n"
						+ "					  L???i upload file ???nh!\r\n" + "					</div>");
				return "Product/UpdateProduct";
			}
		}		

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(product);
			t.commit();
			model.addAttribute("message", "Ch???nh s???a th??nh c??ng");
		} catch (Exception e) {
			System.out.printf(product.getPhoto());
			System.out.printf(product.getName());
			System.out.printf(product.getPhoto());
			System.out.printf(product.getPhoto());
			t.rollback();
			model.addAttribute("message", "Ch???nh s???a th???t b???i");
			
			return "Product/UpdateProduct";
		} finally {
			session.close();
		}
		p = null;
		return "Product/UpdateProduct";
	}
}
