package drintau.accountmanager.webserver.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * 日志唯一ID过滤器
 */
@Slf4j
@Component
public class TraceIdFilter implements Filter {

    private static final String TRACE_ID_KEY = "TRACE_ID";

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("日志唯一ID过滤器初始化。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.debug("日志唯一ID过滤器添加TRACE_ID。");
        try {
            String oldTraceId = MDC.get(TRACE_ID_KEY);
            if (!StringUtils.hasText(oldTraceId)) {
                MDC.put(TRACE_ID_KEY, UUID.randomUUID().toString().replace("-", "").toUpperCase());
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TRACE_ID_KEY);
        }
    }

    @Override
    public void destroy() {
        MDC.clear();
        log.debug("日志唯一ID过滤器销毁。");
    }
}
