package drintau.accountmanager.webserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SayBye implements DisposableBean {

    @Override
    public void destroy() {
        log.info("感谢使用！服务即将停止。");
    }
}
