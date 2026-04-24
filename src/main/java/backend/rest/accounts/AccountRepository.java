package backend.rest.accounts;

import backend.domain.Account;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface AccountRepository extends RestfulJpaRepository<Account, String>{
  
  
}
