package drintau.accountmanager.webserver.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 静态资源访问控制
 */
@Slf4j
@Component
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
