package drintau.accountmanager.gui;

import lombok.Data;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 应用容器：单例
 * 里面的属性应该都是单例的
 */
@Data
public class AMContext {

    private AMContext(){}
    private static final AMContext instance = new AMContext();
    public static AMContext getInstance(){
        return instance;
    }

    // webserver实例
    private ConfigurableApplicationContext webServerContext;

}
