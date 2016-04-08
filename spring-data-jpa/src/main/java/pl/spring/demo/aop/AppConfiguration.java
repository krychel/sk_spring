package pl.spring.demo.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("pl.spring.demo")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AppConfiguration {
	
}
