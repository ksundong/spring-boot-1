package dev.idion.idionkim.board.resolver;

import dev.idion.idionkim.board.annotaion.SocialUser;
import dev.idion.idionkim.board.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(SocialUser.class) != null &&
				parameter.getParameterType().equals(User.class);
	}

	public Object resolveArgument(MethodParameter parameter
			, ModelAndViewContainer mavContainer
			, NativeWebRequest webRequest
			, WebDataBinderFactory binderFactory) throws Exception {
		return null;
	}

}
