package config;

import org.boluanace.DouDiZhu;
import org.springframework.context.annotation.*;

@Configuration("org.boluanace")
@ComponentScan(basePackages = "org.boluanace")
public class JavaConfig {

    @Bean(name = "DouDiZhu")
    public DouDiZhu getDouDIZhu() {
        return new DouDiZhu();
    }
}
