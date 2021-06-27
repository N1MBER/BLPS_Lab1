package com.blps_lab1.demo.main_server.filter;

import com.blps_lab1.demo.main_server.service.KomusUserDetailsService;
import com.blps_lab1.demo.main_server.utils.JWTUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
        "/api/add_product",
        "/api/add_to_cart/*",
        "/api/cart",
        "/api/cart/*",
        "/api/clear_cart",
        "/api/favorite/*",
        "/api/favorites",
        "/api/unfavorite/*",
        "/api/users"
})
public class JwtFilter implements Filter{

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private KomusUserDetailsService komusUserDetailsService;
    Logger logger = LogManager.getLogger(JwtFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.log(Level.INFO, "Filter logs: do filter");
        String token = jwtUtils.getTokenFromRequest((HttpServletRequest) servletRequest);
        if(token != null && jwtUtils.validateToken(token)){
            logger.log(Level.INFO, "Filter logs: token exists");
            try{
                String email = jwtUtils.getEmailFromToken(token);
                UserDetails changeOrgUserDetails = komusUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(changeOrgUserDetails, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.log(Level.INFO, "Filter logs: auth completed");
            }catch (NullPointerException e){
                logger.log(Level.INFO, "Filter logs: auth failed");
                ((HttpServletResponse)servletResponse).setStatus(401);
                ((HttpServletResponse)servletResponse).sendError(401, "Authorization failed. Invalid token");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }



}
