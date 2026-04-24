package backend.oauth2;


import static io.u2ware.common.docs.MockMvcRestDocs.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class Oauth2Tests {
    

    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired MockMvc mvc;
	protected @Autowired Oauth2Docs od;	


    @Test
    public void contextLoads() throws Exception{
        mvc.perform(get("/oauth2/userinfo"))
            .andExpect(is2xx())
            .andExpect(isJson("$.username", "Anonymous"))
            .andDo(print());


        Jwt u = od.jose("user1");
        mvc.perform(get("/oauth2/userinfo").auth(u))
            .andExpect(is2xx())
            .andExpect(isJson("$.subject", "user1"))
            .andDo(print());
    }
}
