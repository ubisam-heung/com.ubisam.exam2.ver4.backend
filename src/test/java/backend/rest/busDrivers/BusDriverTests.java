package backend.rest.busDrivers;

import static io.u2ware.common.docs.MockMvcRestDocs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.domain.BusDriver;
import backend.oauth2.Oauth2Docs;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusDriverTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private Oauth2Docs od;

  @Autowired
  private BusDriverDocs bdd;

  @Autowired
  private BusDriverRepository busDriverRepository;

  // Crud 테스트
  @Test
  void contextLoads() throws Exception{
    // 유저 설정
    Jwt u = od.jose("user");
    // Crud - C
    mvc.perform(post("/rest/busDrivers").content(bdd::newEntity, "김길동")).andExpect(is4xx());
    mvc.perform(post("/rest/busDrivers").content(bdd::newEntity, "김길동").auth(u)).andExpect(is2xx()).andDo(result(bdd::context, "entity1"));

    // Crud - R
    String uri = bdd.context("entity1", "$._links.self.href");
    mvc.perform(post(uri)).andExpect(is4xx());
    mvc.perform(post(uri).auth(u)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = bdd.context("entity1", "$");
    mvc.perform(put(uri).content(bdd::updateEntity, body, "박길동")).andExpect(is4xx());
    mvc.perform(put(uri).content(bdd::updateEntity, body, "박길동").auth(u)).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri)).andExpect(is4xx());
    mvc.perform(delete(uri).auth(u)).andExpect(is2xx());
  }

  // 핸들러 테스트용
  @Test
  void contextLoads2() throws Exception{
    List<BusDriver> result;
    boolean hasResult;

    // 30명의 운전수 추가
    List<BusDriver> busDriverList = new ArrayList<>();
    for(int i = 1; i<=30; i++){
      busDriverList.add(bdd.newEntity(i+"길동", i+"라이센스"));
    }
    busDriverRepository.saveAll(busDriverList);

    // 이름 쿼리
    JpaSpecificationBuilder<BusDriver> nameQuery = JpaSpecificationBuilder.of(BusDriver.class);
    nameQuery.where().and().eq("busDriverName", "3길동");
    result = busDriverRepository.findAll(nameQuery.build());
    hasResult = result.stream().anyMatch(u -> "3길동".equals(u.getBusDriverName()));
    assertEquals(true, hasResult);

    
    // 라이센스 쿼리
    JpaSpecificationBuilder<BusDriver> licenseQuery = JpaSpecificationBuilder.of(BusDriver.class);
    licenseQuery.where().and().eq("busDriverLicense", "3라이센스");
    result = busDriverRepository.findAll(licenseQuery.build());
    hasResult = result.stream().anyMatch(u -> "3라이센스".equals(u.getBusDriverLicense()));
    assertEquals(true, hasResult);
  }
  
  // Search 테스트
  @Test
  void contextLoads3 () throws Exception{

    // 유저 설정
    Jwt u = od.jose("user1");

    // 30명의 운전수 추가
    List<BusDriver> busDriverList = new ArrayList<>();
    for(int i = 1; i<=30; i++){
      busDriverList.add(bdd.newEntity(i+"길동", i+"라이센스"));
    }
    busDriverRepository.saveAll(busDriverList);

    String uri = "/rest/busDrivers/search";

    // Search - 단일 검색
    mvc.perform(post(uri).content(bdd::setSearch, "5길동", "busDriverName")).andExpect(is4xx());
    mvc.perform(post(uri).content(bdd::setSearch, "5길동", "busDriverName").auth(u)).andExpect(is2xx());
    mvc.perform(post(uri).content(bdd::setSearch, "4라이센스", "busDriverLicense")).andExpect(is4xx());
    mvc.perform(post(uri).content(bdd::setSearch, "4라이센스", "busDriverLicense").auth(u)).andExpect(is2xx());

    // Search - 페이지네이션 6개씩 5페이지
    mvc.perform(post(uri).param("size", "6")).andExpect(is4xx());
    mvc.perform(post(uri).param("size", "6").auth(u)).andExpect(is2xx());

    // Search - 정렬 busDriverName, desc
    mvc.perform(post(uri).param("sort", "busDriverName,desc")).andExpect(is4xx());
    mvc.perform(post(uri).param("sort", "busDriverName,desc").auth(u)).andExpect(is2xx());
  }

}

