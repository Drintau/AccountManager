package drintau.accountmanager.webserver.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * 静态资源访问控制
 */
// @Order 对 @WebFilter 无效，默认用类名字符串升序排序
@WebFilter(filterName = "staticResourceFilter",urlPatterns = "*.map")
@Slf4j
public class StaticResourceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("静态资源访问控制过滤器初始化。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
//        log.debug("静态资源访问控制过滤器阻断。");
    }

    @Override
    public void destroy() {
        log.debug("静态资源访问控制过滤器销毁。");
    }
}
