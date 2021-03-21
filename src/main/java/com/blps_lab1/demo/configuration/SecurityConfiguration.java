package com.blps_lab1.demo.configuration;
//
//import com.bslp_lab1.changeorg.filter.JwtFilter;
//import com.bslp_lab1.changeorg.service.ChangeOrgUserDetailsService;
//import com.bslp_lab1.changeorg.utils.JWTutils;
import com.blps_lab1.demo.filter.JwtFilter;
import com.blps_lab1.demo.service.KomusUserDetailsService;
import com.blps_lab1.demo.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTUtils jwtUtils(){
        return new JWTUtils();
    }


    @Bean
    public KomusUserDetailsService changeOrgUserDetailsService(){
        return new KomusUserDetailsService();
    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/auth").permitAll().antMatchers("/petition/add", "/petition/*/subscribe").authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
