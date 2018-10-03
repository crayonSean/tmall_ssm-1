package priv.hailong.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import priv.hailong.pojo.OrderItem;
import priv.hailong.pojo.User;
import priv.hailong.service.CategoryService;
import priv.hailong.service.OrderItemService;


public class OtherInterceptor extends HandlerInterceptorAdapter  {
	@Autowired
	CategoryService categoryService;
	@Autowired
	OrderItemService orderItemService;

	/**
	 * ��ҵ��������������֮ǰ������
	 * - �������false
	 * �ӵ�ǰ������������ִ��������������afterCompletion()���������˳���������
	 * - �������true
	 * ִ����һ����������֪�����е���������ִ�����
	 * ��ִ�б����ص�Controller
	 * Ȼ���������������
	 * �����һ������������ִ�����е�postHandel()����
	 * �����ٴ����һ������������ִ�����е�afterCompletion()����
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 * ��ҵ��������������ִ����ɺ�,������ͼ֮ǰִ�еĶ���
	 * ����modelAndView�м������ݣ����統ǰʱ��
	 */

	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

		/* �����ǻ�ȡ���ﳵ��һ���ж������� */
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int cartTotalItemNumber = 0;
		if (null != user) {
			List<OrderItem> ois = orderItemService.listForCart(user.getId());
			for (OrderItem oi : ois) {
				cartTotalItemNumber += oi.getNumber();
			}
		}
		session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
	}

	/**
	 * ��DispatcherServlet��ȫ����������󱻵���,������������Դ��
	 * <p>
	 * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������afterCompletion()
	 */

	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

//        System.out.println("afterCompletion(), �ڷ�����ͼ֮�󱻵���");
	}
}
