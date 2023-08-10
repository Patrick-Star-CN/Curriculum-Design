package team.star.healthcodesystem.handler;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

/**
 * 登录拦截器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@WebFilter(filterName = "LoginHandler", urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST})
public class LoginHandler implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 登录拦截器
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        if ((!httpRequest.getRequestURI().startsWith("/auth")
                && !httpRequest.getRequestURI().startsWith("/static")
                && !"/test.jsp".equals(httpRequest.getRequestURI())
                && !"/login.jsp".equals(httpRequest.getRequestURI()))
                && (session == null || session.getAttribute("user_id") == null)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/login.jsp");
            return;
        }
        System.out.println(httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}