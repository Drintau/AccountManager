package drintau.accountmanager.webserver.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * 日志唯一ID过滤器
 */
@WebFilter(filterName = "traceIdFilter",urlPatterns = "/*")
@Slf4j
public class TraceIdFilter implements Filter {

    private static final String TRACE_ID_KEY = "TRACE_ID";

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("日志唯一ID过滤器初始化。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String oldTraceId = MDC.get(TRACE_ID_KEY);
        if (!StringUtils.hasText(oldTraceId)) {
            MDC.put(TRACE_ID_KEY, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.clear();
        log.debug("日志唯一ID过滤器销毁。");
    }
}
