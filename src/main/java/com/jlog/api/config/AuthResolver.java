package com.jlog.api.config;

import com.jlog.api.config.data.UserSession;
import com.jlog.api.domain.Session;
import com.jlog.api.exception.Unauthorized;
import com.jlog.api.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            log.error("servletRequest null");
            throw new Unauthorized();
        }

        Cookie[] cookies = servletRequest.getCookies();
        if (cookies.length == 0) {
            log.error("쿠키가 없음");
            throw new Unauthorized();
        }

        String accessToken = cookies[0].getValue();

        // 데이터베이스 사용자 확인작업
        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);

        return new UserSession(session.getMember().getId());
    }

//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
//        String accessToken = webRequest.getHeader("Authorization");
//        if (accessToken == null || accessToken.equals("")) {
//            throw new Unauthorized();
//        }
//
//        // 데이터베이스 사용자 확인작업
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(Unauthorized::new);
//
//        return new UserSession(session.getMember().getId());
//    }

//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
//        String accessToken = webRequest.getParameter("accessToken");
//        if (accessToken == null || accessToken.equals("")) {
//            throw new Unauthorized();
//        }
//        UserSession userSession = new UserSession();
//        userSession.name = accessToken;
//        return userSession;
//    }
}
