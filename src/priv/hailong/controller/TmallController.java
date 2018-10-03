package priv.hailong.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import priv.hailong.service.CategoryService;
import priv.hailong.service.OrderItemService;
import priv.hailong.service.OrderService;
import priv.hailong.service.ProductImageService;
import priv.hailong.service.ProductService;
import priv.hailong.service.PropertyService;
import priv.hailong.service.PropertyValueService;
import priv.hailong.service.ReviewService;
import priv.hailong.service.UserService;
import priv.hailong.pojo.Category;
import priv.hailong.pojo.Order;
import priv.hailong.pojo.OrderItem;
import priv.hailong.pojo.Product;
import priv.hailong.pojo.ProductImage;
import priv.hailong.pojo.PropertyValue;
import priv.hailong.pojo.Review;
import priv.hailong.pojo.User;

import org.springframework.ui.Model;


@Controller
@RequestMapping("")
public class TmallController{
	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	PropertyValueService propertyValueService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	UserService userService;

	@Autowired
	OrderItemService orderItemService;

	@Autowired
	OrderService orderService;
	
	
   //�ض�������ҳ
   @RequestMapping(value="/", method = RequestMethod.GET)
	public String homePage(Model model) {
		return "redirect:/home";
		}
   //�ض�������̨����
   @RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin() {
	   return "redirect:/admin_category_list";
	   }
   //��ҳ
   @RequestMapping(value="/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
	   //TODU:����
	   request.getSession(true).setAttribute("contextPath", "home");
		List<Category> categories = categoryService.list();
		productService.fill(categories);
		productService.fillByRow(categories);
		for(Category category:categories){
			productImageService.fill(category.getProducts());
		}
		model.addAttribute("cs", categories);
		return "home";
		}
   
   
   //��תע��ҳ��
   @RequestMapping("/registerPage")
   public String registerPage(Model model){
   	return "register";
   }
   //ע��
   @RequestMapping("/foreregister")
   public String foreRegister(Model model,
   						      @RequestParam("name") String name,
						      @RequestParam("password") String password){
   		User user = new User();
   		user.setName(name);
   		user.setPassword(password);
   		if(userService.isExist(name)){
			String msg = "�û����Ѿ���ռ�ã�����ʹ��";
			model.addAttribute("msg", msg);
			model.addAttribute("username", user.getName());
	    	return "register";
   		}else{
   			userService.add(user);
   			return "registerSuccess";
   		}
   	}
   	//������½ҳ��
    @RequestMapping("/loginPage")
    public String longinPage(){
    	return "login";
    }
    //���е�½
    @RequestMapping("/forelogin")
    public String foreLogin(Model model,
    						@RequestParam("name") String name,
							@RequestParam("password") String password,
							HttpSession session) {
    	User user = userService.get(name, password);
    	if (user == null) {
    		model.addAttribute("msg", "�˺��������");
    		return "login";
    	}
    	session.setAttribute("user", user);
    	return "redirect:/home";
    }
    //�˳���½
    @RequestMapping("/forelogout")
    public String foreLoginOut(HttpSession session,HttpServletResponse response, Model model){
    	session.removeAttribute("user");
    	return "redirect:/home";
    }

    
    //��Ʒҳ
    @RequestMapping("/foreproduct")
    public String foreProduct(Model model, Integer pid){
    	Product product = productService.get(pid);
    	product.setCategory(categoryService.get(product.getCid()));
    	
        List<ProductImage> pis = productImageService.list(pid);
        List<ProductImage> pisSingle = new ArrayList();
        List<ProductImage> pisDetail = new ArrayList();
        for(ProductImage pi:pis){
        	if (pi.getType().equals("type_single")) {
				pisSingle.add(pi);
			}else{
				pisDetail.add(pi);
			}
        }
    	product.setProductSingleImages(pisSingle);
    	product.setProductDetailImages(pisDetail);
    	productImageService.fill(product);
    	productService.setReviewCount(product);
    	
    	List<OrderItem> orderItems = orderItemService.listByProductId(pid);
    	int saleCount = 0;
		for(OrderItem orderItem:orderItems){
			saleCount += orderItem.getNumber();
		}
    	product.setSaleCount(saleCount);
		model.addAttribute("p", product);		
		List<PropertyValue> propertyValues = propertyValueService.listByProductId(pid);
		for(PropertyValue propertyValue:propertyValues){
			propertyValue.setProperty(propertyService.get(propertyValue.getPtid()));
		}
		model.addAttribute("pvs", propertyValues);
		List<Review> reviews = reviewService.listByProductId(pid);
		model.addAttribute("reviews", reviews);
    	return "product";
    }
    //����ҳ
    @RequestMapping("/forecategory")
    public String foreCategory(Model model, Integer cid, String sort){
    	Category category = categoryService.get(cid);
		productService.fill(category);
		productImageService.fill(category.getProducts());
		List<Product> products = category.getProducts();
    	for (Product product : products) {
			product.setReviewCount(reviewService.getCount(product.getId()));
		}
		if (null != sort) {
			switch (sort) {
				case "all":
					Collections.sort(products, Comparator.comparing(Product::getReviewCount));
					break;
				case "review":
					Collections.sort(products, Comparator.comparing(Product::getReviewCount));
					break;
				case "date":
					Collections.sort(products, Comparator.comparing(Product::getCreateDate));
					break;
				case "saleCount":
					Collections.sort(products, Comparator.comparing(Product::getPromotePrice));
					break;
				case "price":
					Collections.sort(products, Comparator.comparing(Product::getPromotePrice));
					break;
			}
		}
		model.addAttribute("c", category);
    	return "category";
    }
    //����ҳ
    @RequestMapping(value="/foresearch", method = RequestMethod.POST)
    public String foreSearch(Model model, String keyword) throws IOException{
		List<Category> categories = categoryService.list();
		model.addAttribute("cs", categories);
//		new String(keyword.getBytes("iso-8859-1"), "utf-8");
		List<Product> products = productService.search(keyword);
		productImageService.fill(products);
    	model.addAttribute("ps", products);
    	return "searchResult";
    }

    
	/**
	 * �������򣨼�����OrderItem���Ҫ�����������������
	 * 1.��������Ʒ�Ѿ������ڹ��ﳵ�У���ôֻ��Ҫ��Ӧ�ĵ��������Ϳ�����
	 * 2.��������ڶ�Ӧ��OrderItem���ô������һ�������OrderItem��
	 * - ǰ���������Ѿ���¼
	 *
	 * @param pid ��Ʒ��ID
	 * @param number     ���������
	 * @param session    session���ڻ�ȡuser��Ϣ
	 * @return
	 */
	@RequestMapping("/forebuyone")
	public String foreBuyone(Integer pid, Integer num, HttpSession session, Model model) {
		int orderItemId = 0;
		User user = (User) session.getAttribute("user");
		boolean found = false;
		List<OrderItem> orderItems = orderItemService.listByUserId(user.getId());
		for (OrderItem orderItem : orderItems) {
			if (orderItem.getPid().intValue() == pid.intValue()&&orderItem.getOid() == null) {
				orderItem.setNumber(orderItem.getNumber() + num);
				orderItemService.update(orderItem);
				orderItemId = orderItem.getId();
				found = true;
				break;
			}
		}
		if (!found) {
			OrderItem orderItem = new OrderItem();
			orderItem.setUid(user.getId());
			orderItem.setNumber(num);
			orderItem.setPid(pid);
			orderItemService.add(orderItem);
			orderItemId = orderItem.getId();
		}
	return "redirect:/forebuy?oiid=" + orderItemId;}
	//ȷ������
	@RequestMapping("forebuy")
	public String forebuy(Model model, String[] oiid, HttpSession session) {
		List<OrderItem> orderItems = new ArrayList<>();
		float total = 0;
		for (String strId : oiid) {
			int id = Integer.parseInt(strId);
			OrderItem oi = orderItemService.getById(id);
			productImageService.fill(oi.getProduct());
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
			orderItems.add(oi);
		}
		model.addAttribute("total", total);
		session.setAttribute("ois", orderItems);
		return "buy";
	}
	//�ض�����֧��
	@RequestMapping(value="/forecreateOrder", method = RequestMethod.POST)
	public String foreCreateOrder(Model model, Order order,HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		order.setOrderCode(orderCode);
		order.setCreateDate(new Date());
		order.setUid(user.getId());
		order.setStatus(OrderService.waitPay);
		List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
		float total = orderService.add(order, orderItems);
		return "redirect:/forealipay?oid=" + order.getId() + "&total=" + total;
	}
	//֧����
	@RequestMapping(value="/forealipay")
	public String foraliPay(Model model) {
		return "alipay";
	}
	//֧�����
	@RequestMapping("forepayed")
	public String payed(int oid, float total, Model model) {
		Order order = orderService.get(oid);
		order.setStatus(OrderService.waitDelivery);
		order.setPayDate(new Date());
		orderService.update(order);
		model.addAttribute("o", order);
		return "payed";
	}
	
	
	/**
	 * ���빺�ﳵ��������buyone()������Щ���ƣ������ز�ͬ
	 * ��Ȼ��Ҫ����������OrderItem���������������
	 * 1.��������Ʒ�Ѿ������ڹ��ﳵ�У���ôֻ��Ҫ��Ӧ�ĵ��������Ϳ�����
	 * 2.��������ڶ�Ӧ��OrderItem���ô������һ�������OrderItem��
	 * - ǰ���������Ѿ���¼ 
	 *
	 * @param product_id
	 * @param num
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("foreaddCart")
	@ResponseBody
	public String foreAddCart(Integer pid, Integer num, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		boolean found = false;
		List<OrderItem> ois = orderItemService.listByUserId(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId().intValue() == pid.intValue()&&oi.getOid() == null) {
				oi.setNumber(oi.getNumber() + num);
				orderItemService.update(oi);
				found = true;
				break;
			}
		}
		if (!found) {
			OrderItem oi = new OrderItem();
			oi.setUid(user.getId());
			oi.setNumber(num);
			oi.setPid(pid);
			orderItemService.add(oi);
		}
		return "success";
	}
	
	
	/**
	 * �鿴���ﳵ������
	 * 1.����ͨ��session��ȡ����ǰ���û�
	 * 2.��ȡ����û������Ķ�����ļ���
	 *
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/forecart")
	public String foreCart(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<OrderItem> orderItems = orderItemService.listForCart(user.getId());
		for(OrderItem orderItem : orderItems){
			Product product = productService.get(orderItem.getPid());
			productImageService.fill(product);
			orderItem.setProduct(product);
		}
		model.addAttribute("ois", orderItems);
		return "cart";
	}
	//�ı䶩����Ajax
	@RequestMapping("forechangeOrderItem")
	@ResponseBody
	public String foreChangeOrderItem(Model model, HttpSession session, int pid, int number) {
		User user = (User) session.getAttribute("user");
		if (null == user)
			return "fail";

		List<OrderItem> ois = orderItemService.listByUserId(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId().intValue() == pid) {
				oi.setNumber(number);
				orderItemService.update(oi);
				break;
			}
		}
		return "success";
	}
	//������ɾ��
	@RequestMapping("foredeleteOrderItem")
	@ResponseBody
	public String foreDeleteOrderItem(Model model, HttpSession session, Integer oiid) {
		User user = (User) session.getAttribute("user");
		if (null == user)
			return "fail";
		orderItemService.delete(oiid);
		return "success";
		}

	
    //��֤��½״̬
	@RequestMapping("/forecheckLogin")
	@ResponseBody
	public String foreCheckLogin(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null)
			return "success";
		return "fail";
	}
	//���ٵ�½
	@RequestMapping("/foreloginAjax")
	@ResponseBody
	public String foreLoginAjax(HttpSession session, String name, String password){
		User user = userService.get(name, password);
		if (user != null){
			session.setAttribute("user", user);
			return "success";
		}
		return "fail";
	}

	
	//�ҵĶ���
	@RequestMapping("forebought")
	public String foreBought(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Order> orders = orderService.list(user.getId(), OrderService.delete);
		orderItemService.fill(orders);
		for(Order order:orders){
			List<OrderItem> orderItems = orderItemService.getByOrderId(order.getId());
			order.setOrderItems(orderItems);			
			for(OrderItem orderItem : orderItems){
				Product product = productService.get(orderItem.getPid());
				productImageService.fill(product);
				orderItem.setProduct(product);
			}
		}
		model.addAttribute("os", orders);
		return "bought";
	}
	//ɾ������
	@RequestMapping("foredeleteOrder")
	@ResponseBody
	public String foreDeleteOrder(Model model, Integer oid) {
		Order o = orderService.get(oid);
		o.setStatus(OrderService.delete);
		orderService.update(o);
		return "success";
	}
	
	
	//ȷ��֧��--ȷ���ջ�
	@RequestMapping("foreconfirmPay")
	public String foreConfirmPay(Model model, Integer oid) {
		Order order = orderService.get(oid);
		orderItemService.fill(order);
		for(OrderItem orderItem : order.getOrderItems()){
			Product product = productService.get(orderItem.getPid());
			productImageService.fill(product);
			orderItem.setProduct(product);
		}
		model.addAttribute("o", order);
		return "confirmPay";
	}
	//�����������
	@RequestMapping("foreorderConfirmed")
	public String foreOrderConfirmed(Model model, Integer oid) {
		Order o = orderService.get(oid);
		o.setStatus(OrderService.waitReview);
		o.setConfirmDate(new Date());
		orderService.update(o);
		return "orderConfirmed";
	}
	//ȥ����
	@RequestMapping("forereview")
	public String foreReview(Model model, Integer oid) {
		Order order = orderService.get(oid);
		orderItemService.fill(order);
		Product product = order.getOrderItems().get(0).getProduct();
		productImageService.fill(product);
		List<Review> reviews = reviewService.listByProductId(product.getId());
		productService.setReviewCount(product);
		model.addAttribute("p", product);
		model.addAttribute("o", order);
		model.addAttribute("reviews", reviews);
		return "review";
	}
	//�ύ����
	@RequestMapping("foredoreview")
	public String foreDoReview(Model model, HttpSession session,
			@RequestParam("oid") Integer oid,
			@RequestParam("pid") Integer pid,
			String content) {
		
		Order order = orderService.get(oid);
		order.setStatus(OrderService.finish);
		orderService.update(order);
		
		User user = (User) session.getAttribute("user");
		Review review = new Review();
		review.setContent(content);
		review.setPid(pid);
		review.setCreateDate(new Date());
		review.setUid(user.getId());
		reviewService.add(review);
		return "redirect:/forereview?oid=" + oid + "&showonly=true";
	}
}