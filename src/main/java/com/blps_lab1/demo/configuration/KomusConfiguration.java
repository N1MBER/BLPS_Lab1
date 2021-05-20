package com.blps_lab1.demo.configuration;


import com.blps_lab1.demo.DTO.*;
import com.blps_lab1.demo.beans.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class KomusConfiguration extends WebMvcConfigurerAdapter {



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    @Scope(scopeName = "prototype")
    public User getUser(){ return new User(); }

    @Bean
    @Scope(scopeName = "prototype")
    public Payment getPayment(){ return new Payment(); }

    @Bean
    @Scope(scopeName = "prototype")
    public Product getProduct(){
        return new Product();
    }


    @Bean
    @Scope(scopeName = "prototype")
    public Notification getNotification(){
        return new Notification();
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Order getOrder() { return new Order();}

    @Bean
    @Scope(scopeName = "prototype")
    public ResponseMessageDTO getResponseMessageDTO(){
        return new ResponseMessageDTO();
    }

    @Bean(name = "UserDTO")
    @Scope(scopeName = "prototype")
    public UserDTO getUserDTO(){
        return new UserDTO();
    }

    @Bean(name = "ProductDTO")
    @Scope(scopeName = "prototype")
    public ProductDTO getProductDTO() {
        return new ProductDTO();
    }

    @Bean(name = "NotificationDTO")
    @Scope(scopeName = "prototype")
    public NotificationDTO getNotificationDTO() {
        return new NotificationDTO();
    }

    @Bean(name = "OrderDTO")
    @Scope(scopeName = "prototype")
    public OrderDTO getOrderDTO() { return new OrderDTO();}
}
