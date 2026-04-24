package backend.rest.busRoutes;

import static io.u2ware.common.docs.MockMvcRestDocs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.domain.BusRoute;
import backend.oauth2.Oauth2Docs;
import backend.rest.busStops.BusStopDocs;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusRouteTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private Oauth2Docs od;

  @Autowired
  private BusRouteDocs brd;

  @Autowired
  private BusStopDocs bsd;

  @Autowired
  private BusRouteRepository busRouteRepository;

  // Crud 테스트
  @Test
  void contextLoads() throws Exception{
    
    // 유저 설정
    Jwt u = od.jose("user");

    // 사전 설정
    mvc.perform(post("/rest/busRoutes").content(bsd::newEntity, "가산정류장").auth(u)).andDo(result(bsd::context, "bsEntity1")).andExpect(is2xx());
    String busRouteLink1 = bsd.context("bsEntity1", "$._links.self.href");
    mvc.perform(post("/rest/busRoutes").content(bsd::newEntity, "남구로정류장").auth(u)).andDo(result(bsd::context, "bsEntity2")).andExpect(is2xx());
    String busRouteLink2 = bsd.context("bsEntity2", "$._links.self.href");
    mvc.perform(post("/rest/busRoutes").content(bsd::newEntity, "대림정류장").auth(u)).andDo(result(bsd::context, "bsEntity3")).andExpect(is2xx());
    String busRouteLink3 = bsd.context("bsEntity3", "$._links.self.href");

    Map<String, Object> req = new HashMap<>();
    req.put("title", "entity1");
    req.put("busRouteName", "가산노선");
    req.put("busRouteStart", "가산노선시작1");
    req.put("busRouteEnd", "가산노선끝1");
    req.put("busRouteLinks", Set.of(busRouteLink1, busRouteLink2));
     
    // Crud - C
    mvc.perform(post("/rest/busRoutes").content(req)).andExpect(is4xx());
    mvc.perform(post("/rest/busRoutes").content(req).auth(u)).andExpect(is2xx()).andDo(result(brd::context, "entity1"));
    String uri = brd.context("entity1", "$._links.self.href");
    req = brd.context("entity1", "$");
  
    // Crud - R
    mvc.perform(post(uri)).andExpect(is4xx());
    mvc.perform(post(uri).auth(u)).andExpect(is2xx());
 
    // Crud - U
    req.put("busRouteName", "남구로노선");
    req.put("busRouteLinks", Set.of(busRouteLink2, busRouteLink3));
    mvc.perform(put(uri).content(req)).andExpect(is4xx());
    mvc.perform(put(uri).content(req).auth(u)).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri)).andExpect(is4xx());
    mvc.perform(delete(uri).auth(u)).andExpect(is2xx());

  }

  // 핸들러 테스트용
  @Test
  void contextLoads2() throws Exception{
    List<BusRoute> result;
    boolean hasResult;

    // 30개의 노선 추가
    List<BusRoute> busRouteList = new ArrayList<>();
    for(int i = 1; i<=30; i++){
      busRouteList.add(brd.newEntity(i+"노선", i+"시작", i+"끝"));
    }
    busRouteRepository.saveAll(busRouteList);
  
    // 이름 쿼리
    JpaSpecificationBuilder<BusRoute> nameQuery = JpaSpecificationBuilder.of(BusRoute.class);
    nameQuery.where().and().eq("busRouteName", "3노선");
    result = busRouteRepository.findAll(nameQuery.build());
    hasResult = result.stream().anyMatch(u -> "3노선".equals(u.getBusRouteName()));
    assertEquals(true, hasResult);
    
    
    // 시작점 쿼리
    JpaSpecificationBuilder<BusRoute> startQuery = JpaSpecificationBuilder.of(BusRoute.class);
    startQuery.where().and().eq("busRouteStart", "2시작");
    result = busRouteRepository.findAll(startQuery.build());
    hasResult = result.stream().anyMatch(u -> "2시작".equals(u.getBusRouteStart()));
    assertEquals(true, hasResult);

    // 종점 쿼리
    JpaSpecificationBuilder<BusRoute> endQuery = JpaSpecificationBuilder.of(BusRoute.class);
    endQuery.where().and().eq("busRouteEnd", "2끝");
    result = busRouteRepository.findAll(endQuery.build());
    hasResult = result.stream().anyMatch(u -> "2끝".equals(u.getBusRouteEnd()));
    assertEquals(true, hasResult);
  
  }
 
  // Search 테스트
  @Test
  void contextLoads3 () throws Exception{
 
    // 유저 설정
    Jwt u = od.jose("user1");
 
    // 30개의 노선 추가
    List<BusRoute> busRouteList = new ArrayList<>();
    for(int i = 1; i<=30; i++){
      busRouteList.add(brd.newEntity(i+"노선", i+"시작", i+"끝"));
    }
    busRouteRepository.saveAll(busRouteList);

    String uri = "/rest/busRoutes/search";

    // Search - 단일 검색
    mvc.perform(post(uri).content(brd::setSearch, "5노선", "busRouteName")).andExpect(is4xx());
    mvc.perform(post(uri).content(brd::setSearch, "5노선", "busRouteName").auth(u)).andExpect(is2xx());
    mvc.perform(post(uri).content(brd::setSearch, "4시작", "busRouteStart")).andExpect(is4xx());
    mvc.perform(post(uri).content(brd::setSearch, "4시작", "busRouteStart").auth(u)).andExpect(is2xx());
    mvc.perform(post(uri).content(brd::setSearch, "3끝", "busRouteEnd")).andExpect(is4xx());
    mvc.perform(post(uri).content(brd::setSearch, "3끝", "busRouteEnd").auth(u)).andExpect(is2xx());
 
    // Search - 페이지네이션 6개씩 5페이지
    mvc.perform(post(uri).param("size", "6")).andExpect(is4xx());
    mvc.perform(post(uri).param("size", "6").auth(u)).andExpect(is2xx());

    // Search - 정렬 busRouteName, desc
    mvc.perform(post(uri).param("sort", "busRouteName,desc")).andExpect(is4xx());
    mvc.perform(post(uri).param("sort", "busRouteName,desc").auth(u)).andExpect(is2xx());

  }

}
