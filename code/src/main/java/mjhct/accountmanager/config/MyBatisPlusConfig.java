package mjhct.accountmanager.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("mjhct.accountmanager.dao.mapper")
public class MyBatisPlusConfig {

}
