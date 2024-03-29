package dev.idion.idionkim.board.resolver;

import dev.idion.idionkim.board.annotaion.SocialUser;
import dev.idion.idionkim.board.domain.User;
import dev.idion.idionkim.board.domain.enums.SocialType;
import dev.idion.idionkim.board.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static dev.idion.idionkim.board.domain.enums.SocialType.FACEBOOK;
import static dev.idion.idionkim.board.domain.enums.SocialType.GOOGLE;
import static dev.idion.idionkim.board.domain.enums.SocialType.KAKAO;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private UserRepository userRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(SocialUser.class) != null &&
				parameter.getParameterType().equals(User.class);
	}

	public Object resolveArgument(MethodParameter parameter
			, ModelAndViewContainer mavContainer
			, NativeWebRequest webRequest
			, WebDataBinderFactory binderFactory) throws Exception {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		User user = (User) session.getAttribute("user");
		return getUser(user, session);
	}

	private User getUser(User user, HttpSession session) {
		if (user == null) {
			try {
				OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				Map<String, Object> map = authentication.getPrincipal().getAttributes();
				User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);

				user = userRepository.findByEmail(convertUser.getEmail());
				if (user == null) { user = userRepository.save(convertUser); }

				setRoleIfNotSame(user, authentication, map);
				session.setAttribute("user", user);
			} catch (Exception e) {
				return user;
			}
		}
		return user;
	}

	private User convertUser(String authority, Map<String, Object> map) {
		if (FACEBOOK.getValue().equals(authority)) return getModernUser(FACEBOOK, map);
		else if (GOOGLE.getValue().equals(authority)) return getModernUser(GOOGLE, map);
		else if (KAKAO.getValue().equals(authority)) return getKaKaoUser(map);
		return null;
	}

	private User getModernUser(SocialType socialType, Map<String, Object> map) {
		return User.builder()
				.name(String.valueOf(map.get("name")))
				.email(String.valueOf(map.get("eamil")))
				.principal(String.valueOf(map.get("id")))
				.socialType(socialType)
				.createdDate(LocalDateTime.now())
				.build();
	}

	private User getKaKaoUser(Map<String, Object> map) {
		HashMap<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
		return User.builder()
				.name(propertyMap.get("nickname"))
				.email(String.valueOf(map.get("id")))
				.principal(String.valueOf(map.get("id")))
				.socialType(KAKAO)
				.createdDate(LocalDateTime.now())
				.build();
	}

	private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
			SecurityContextHolder.getContext().setAuthentication(new
					UsernamePasswordAuthenticationToken(map, "N/A",
					AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
		}
	}

}
