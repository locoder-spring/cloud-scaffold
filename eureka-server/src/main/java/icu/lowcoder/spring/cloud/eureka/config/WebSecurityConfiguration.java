package icu.lowcoder.spring.cloud.eureka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${icu.lowcoder.spring.commons.management.security.enabled:true}")
    private boolean managementSecurityEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (managementSecurityEnabled) {
            http
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic()
                .and().csrf().disable();

        } else {
            http
                .authorizeRequests()
                    .anyRequest().permitAll()
                .and().csrf().disable();
        }
    }
}
