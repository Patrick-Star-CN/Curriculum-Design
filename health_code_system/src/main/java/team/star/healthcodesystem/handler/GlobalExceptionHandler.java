package team.star.healthcodesystem.handler;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.exception.AppException;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 全局异常处理
 *
 * @author Patrick_Star
 * @version 1.0
 */
@WebFilter(filterName = "GlobalExceptionHandler", urlPatterns = "/*")
public class GlobalExceptionHandler implements Filter {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 全局异常处理
     *
     * @param servletRequest  请求
     * @param servletResponse 响应
     * @param filterChain     过滤器链
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            logger.info(httpRequest.getRequestURI());
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            servletRequest.setAttribute("exception", e);
            servletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
