package backend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import io.u2ware.common.oauth2.jose.JoseKeyEncryptor;
import io.u2ware.common.stomp.client.config.EnableWebsocketStompClient;
import io.u2ware.common.stomp.client.config.WebsocketStompProperties;


@Configuration
@EnableWebsocketStompClient
@Controller
public class ApplicationStompConfig {

	protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired JwtDecoder jwtDecoder;
    protected @Autowired WebsocketStompProperties websocketStompProperties;

    @PostMapping("/oauth2/token")
    public @ResponseBody ResponseEntity<Object> oauth2Token(@RequestBody String token) {
        
        try{
            Jwt jwt = JoseKeyEncryptor.decrypt(jwtDecoder, ()->{return token;});
            logger.info(jwt.getSubject());

            String url = UriComponentsBuilder.fromUriString(websocketStompProperties.getUrl()).queryParam("access_token", token).toUriString();
            logger.info(url);

            websocketStompProperties.setUrl(url);
            return ResponseEntity.ok("ok");

        }catch(Exception e){
            logger.info("", e);
            return ResponseEntity.notFound().build();
        }
    }
}