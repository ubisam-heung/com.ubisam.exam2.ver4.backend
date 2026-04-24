package backend.domain.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationContext {

    public static HttpServletRequest httpServletRequest() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes request = (ServletRequestAttributes)attrs;
        return request.getRequest();
    }

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @SuppressWarnings("unchecked")
    public static Collection<GrantedAuthority> authorities() {
        if(authentication() == null) return Collections.EMPTY_LIST;
        return (Collection<GrantedAuthority>) authentication().getAuthorities();
    }

    public static Boolean isAnonymousUser(){
        try{
            // return ! "anonymousUser".equals(AuthenticationContext.authentication().getName());
            // return authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) 
            return (authentication() instanceof AnonymousAuthenticationToken);
        }catch(Exception e){
            return false;
        }
    }    

    public static Boolean hasAuthorities(String... roles) {
        Collection<GrantedAuthority> authorities = authorities();
        if(roles.length < 1 || authorities.size() < 1) return false;

        boolean result = true;
        for(String role : roles) {
            if(StringUtils.hasLength(role)) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                result = result && authorities.contains(authority);    
            }else{
                result = result && false;
            }
        }
        return result; 
    }
}
