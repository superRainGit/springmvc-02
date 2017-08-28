package cn.zhang.springmvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 可以使用 @ResponseStatus 注解来修饰异常类  那么在发生对应的异常的时候就不是简单的将错误
 * 信息直接返回到页面   然后报一个 500 的服务器错误  而是会根据注解中的 value 和 reason 属性
 * 来指定页面的响应码和相应的错误信息
 * @author zhang
 *
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="用户名与密码不匹配")
public class UserNotMatchPassException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
}
