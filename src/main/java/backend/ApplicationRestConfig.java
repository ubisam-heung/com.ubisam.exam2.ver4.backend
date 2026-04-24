package backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import io.u2ware.common.data.jpa.config.EnableRestfulJpaRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;



@Configuration
@EnableJpaRepositories
@EnableJpaAuditing 
@EnableRestfulJpaRepositories
public class ApplicationRestConfig implements RepositoryRestConfigurer  {

    @Autowired
    private EntityManager entityManager;
    

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        cors.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(false)
                .maxAge(30000);
                
        Class<?>[] classes = entityManager.getMetamodel()
                .getEntities().stream().map(Type::getJavaType).toArray(Class[]::new);
        config.exposeIdsFor(classes);

        config.setReturnBodyOnCreate(true);
        config.setReturnBodyOnUpdate(true);

        config.setBasePath("/rest");
    }


}
