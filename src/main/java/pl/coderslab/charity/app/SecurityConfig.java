package pl.coderslab.charity.app;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.coderslab.charity.service.CustomerUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerUserDetailsService customerUserDetailsService;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SecurityConfig(CustomerUserDetailsService customerUserDetailsService, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/register-confirmation").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/institution").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/contactForm-confirmation").permitAll()
                .antMatchers("/loginCheck/**").permitAll()
                .antMatchers("/contactForm").permitAll()
                .antMatchers("/passwordRestarted/**").permitAll()
                .antMatchers("/passwordRestartedStep2").permitAll()
                .antMatchers("/resetPassword").permitAll()
                .antMatchers("/resetPassword-confirmation").permitAll()
                .antMatchers("/js/**", "/css/**", "/fonts/**", "/images/**", "/templates/fragments/**").permitAll()
                .antMatchers("/logged", "/logged/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("email") // username
                .passwordParameter("password") // password
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index");
//                .and()
//                .csrf()
//                .disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("pass").roles("USER")
                .and()
                .withUser("admin").password("pass").roles("ADMIN");
    }

}