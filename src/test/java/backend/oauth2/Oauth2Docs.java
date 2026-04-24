package backend.oauth2;


import java.util.Arrays;
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
public class Oauth2Docs extends MockMvcRestDocs {
    
    protected @Autowired(required = false) @Lazy JwtEncoder jwtEncoder;
    protected @Autowired(required = false) @Lazy JwtDecoder jwtDecoder;

    public Jwt jose(String username, String... authorities) {

        try{
            return JoseKeyEncryptor.encrypt(jwtEncoder, claims->{

                claims.put("sub", username);
                if(! ObjectUtils.isEmpty(authorities)){
                    claims.put("authorities", Arrays.asList(authorities));
                }
                claims.put("jti", UUID.randomUUID().toString());
            });
    
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }   
}
