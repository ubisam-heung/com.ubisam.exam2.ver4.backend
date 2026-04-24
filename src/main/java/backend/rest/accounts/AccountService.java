package backend.rest.accounts;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import backend.domain.Account;
import backend.domain.properties.AttributesSet;
import io.u2ware.common.oauth2.jwt.JwtClaims;
import io.u2ware.common.oauth2.security.SimpleJwtAuthenticationMapper;
import io.u2ware.common.oauth2.security.SimpleJwtAuthoritiesConverter;
import io.u2ware.common.oauth2.security.SimpleJwtGrantedAuthoritiesConverter;

@Component
public class AccountService implements SimpleJwtAuthenticationMapper, SimpleJwtAuthoritiesConverter{

  protected Log logger = LogFactory.getLog(getClass());

  @Autowired
  protected AccountRepository accountRepository;

  public Collection<GrantedAuthority> convert(Jwt jwt){
    Collection<GrantedAuthority> authorities = SimpleJwtGrantedAuthoritiesConverter.authorities(jwt);
    logger.info("authorities by jwt: "+authorities);

    String accountId = jwt.getSubject();
    accountRepository.findById(accountId).ifPresentOrElse((a)->{
      AttributesSet roles = a.getRoles();
      Collection<GrantedAuthority> add = Account.getAuthorities(roles);
      authorities.addAll(add);
      logger.info("authorities by exists account: "+ add);
  
    }, () -> {
      AttributesSet roles = accountRepository.count() > 0 
        ? new AttributesSet(Arrays.asList("ROLE_USER"))
        : new AttributesSet(Arrays.asList("ROLE_ADMIN"));
      Collection<GrantedAuthority> add = Account.getAuthorities(roles);
      authorities.addAll(add);
      logger.info("authorities by new account: "+ add);

      String provider = jwt.getClaimAsString(JwtClaims.provider.name());
      String username = jwt.getClaimAsString(JwtClaims.provider_user.name());
      Account a = new Account();
      a.setId(accountId);
      a.setProvider(provider);
      a.setUsername(username);
      a.setRoles(roles);
      SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt, authorities));
      a = accountRepository.save(a);
    });
    logger.info(("authorities: "+ authorities));
    return authorities;
  }

  @Override
  public Map<String, Object> map(JwtAuthenticationToken token){
    Jwt jwt = token.getToken();
    String subject = jwt.getSubject();
    AtomicReference<String> username = new AtomicReference<>();

    accountRepository.findById(subject).ifPresentOrElse((a)->{
      username.set(a.getUsername());
    }, ()->{
      username.set(jwt.getClaimAsString(JwtClaims.provider_user.name()));
    });
    List<String> authorities = token.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

    Map<String, Object> info = new HashMap<>();
    info.put("subject", subject);
    info.put("username", username.get());
    info.put("authorities", authorities);
    return info;
  }
  
}
