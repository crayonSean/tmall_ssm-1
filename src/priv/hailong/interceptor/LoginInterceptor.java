package priv.hailong.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import priv.hailong.pojo.User;


public class LoginInterceptor extends HandlerInterceptorAdapter {
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

		HttpSession session = request.getSession();
		
		String[] noNeedAuthPage = new String[]{
				"/Tmall/",
				"/Tmall/home",
				"/Tmall/foresearch",
				"/Tmall/foreproduct",
				"/Tmall/forecategory",
				"/Tmall/forelogin",
				"/Tmall/loginPage",
				"/Tmall/forelogout",
				"/Tmall/registerPage",
				"/Tmall/foreregister",
				"/Tmall/forecheckLogin",
				"/Tmall/foreloginAjax"
		};
		String uri = request.getRequestURI();
		if (!Arrays.asList(noNeedAuthPage).contains(uri)) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				response.sendRedirect("loginPage");
				return false;
			}
		}
		return true;
	}
	/**
	 * ��ҵ��������������ִ����ɺ�������ͼ֮ǰִ�еĶ���
	 * ���� modelAndView �м������ݣ����統ǰ��ʱ��
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * ��DispatcherServlet��ȫ����������󱻵��ã�������������Դ��
	 * �����������׳��쳣ʱ����ӵ�ǰ����������ִ�����е���������afterCompletion()����
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
