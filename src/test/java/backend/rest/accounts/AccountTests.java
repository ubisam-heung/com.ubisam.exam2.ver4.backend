package backend.rest.accounts;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.is4xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

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
public class AccountTests {

  protected Log logger = LogFactory.getLog(getClass());

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected AccountDocs ad;

  @Test
  void contextLoads() throws Exception{

    // Create
    Jwt u = ad.jose("admin", "admin", "관리자", "ROLE_ADMIN");
    mvc.perform(post("/rest/accounts/search")).andExpect(is4xx());
    mvc.perform(post("/rest/accounts/search").auth(u)).andExpect(is2xx()).andDo(result(ad::context, "account1")).andDo(print());

    // Setting
    String url = ad.context("account1", "$._embedded.accounts[0]._links.self.href");
    Map<String, Object> body = ad.context("account1");
    body.put("provider", ad.context("account1", "$._embedded.accounts[0].provider"));
    body.put("roles", ad.context("account1", "$._embedded.accounts[0].roles"));

    // Check
    assertEquals(ad.context("account1", "$._embedded.accounts[0].username"), "관리자");

    // Update
    mvc.perform(put(url).auth(u).content(ad::updateEntity, body, "관리자2")).andExpect(is2xx()).andDo(result(ad::context, "account2"));

    // Check
    assertEquals(ad.context("account2", "$.username"), "관리자2");

    // Delete
    mvc.perform(delete(url).auth(u)).andExpect(is2xx());
  }
  
}
