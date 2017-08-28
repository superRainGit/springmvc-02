package cn.zhang.springmvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 实现一个自定义的简单的拦截器
 * @author zhang
 *
 */
public class FirstInterceptor implements HandlerInterceptor {

	/**
	 * 在目标方法执行之前被调用
	 * 如果该方法返回 true: 会继续执行后续的拦截器的方法和目标方法
	 * 如果该方法返回 false: 就不会继续执行后续的拦截器的方法和目标方法
	 * 可以做的事情: 权限 事务 日志...
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("FirstInterceptor.preHandle()");
		return true;
	}

	/**
	 * 在目标方法调用之后  渲染视图之前被调用
	 * 可以对 request 作用域以及 ModelAndView 视图进行修改
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("FirstInterceptor.postHandle()");
	}

	/**
	 * 在渲染视图之后被调用
	 * 可以用来释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("FirstInterceptor.afterCompletion()");
	}

}
