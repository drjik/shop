package drjik.shop.сonfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(authorization -> {
           authorization
                   .requestMatchers("/security_controller/second_resource")
                   .authenticated();
           authorization
                   .requestMatchers("/cart")
                   .authenticated();
           authorization
                   .requestMatchers("/form_controller/add_product")
                   .hasRole("admin");
            authorization
                    .requestMatchers("/form_controller/add_category")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/form_controller/check_recalls")
                    .hasRole("admin");
           authorization
                   .requestMatchers("/security_controller/current_user")
                   .authenticated();
           authorization
                   .anyRequest()
                   .permitAll();

        });
        httpSecurity.formLogin(formLoginConfigurer -> {
          formLoginConfigurer.defaultSuccessUrl("/products");
        });
        return httpSecurity.build();
    }
}
