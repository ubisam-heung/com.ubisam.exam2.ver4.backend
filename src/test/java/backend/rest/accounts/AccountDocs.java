package backend.rest.accounts;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import io.u2ware.common.docs.MockMvcRestDocs;
import io.u2ware.common.oauth2.jose.JoseKeyEncryptor;

@Component
public class AccountDocs extends MockMvcRestDocs{

  @Autowired(required = false)
  @Lazy 
  protected JwtEncoder jwtEncoder;

  @Autowired(required = false)
  @Lazy 
  protected JwtDecoder jwtDecoder;

  public Jwt jose(String username, String provider, String providerUser, String... authorities) {

      try{
          return JoseKeyEncryptor.encrypt(jwtEncoder, claims->{
              claims.put("sub", username);
              if(!ObjectUtils.isEmpty(authorities)){
                  claims.put("authorities", Arrays.asList(authorities));
              }
              claims.put("jti", UUID.randomUUID().toString());
              claims.put("provider", provider);
              claims.put("provider_user", providerUser);
          });
  
      }catch(Exception e){
          e.printStackTrace();
          return null;
      }
  }   
  
  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("username", entity);
    return body;
  }
  
}
