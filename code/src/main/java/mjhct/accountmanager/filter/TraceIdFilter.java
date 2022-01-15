package mjhct.accountmanager.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * 日志唯一ID过滤器
 */
@WebFilter(filterName = "traceIdFilter",urlPatterns = "/*")
public class TraceIdFilter implements Filter {

    private static final String TRACE_ID_KEY = "TRACE_ID";

    private static final Logger logger = LoggerFactory.getLogger(TraceIdFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("日志唯一ID过滤器初始化。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String oldTraceId = MDC.get(TRACE_ID_KEY);
        if (!StringUtils.hasText(oldTraceId)) {
            MDC.put(TRACE_ID_KEY, UUID.randomUUID().toString().replaceAll("-", ""));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.clear();
        logger.debug("日志唯一ID过滤器销毁。");
    }
}
