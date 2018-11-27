package com.enfiny.tokens.tokengenerator.repository;

import com.enfiny.tokens.tokengenerator.enums.Status;
import com.enfiny.tokens.tokengenerator.model.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByUsername(String username);

    Long countByStatus(Status active);

    List<Client> findByStatusAndUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(Status active, String search, String search1, Pageable pageValue);

    Long countByStatusAndUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(Status active, String search, String search1);

    List<Client> findAllByStatus(Status active, Pageable pageValue);

   // @Modifying
   // @Query(value = "select distinct * FROM client s inner join app a on s.id=a.client_id WHERE s.username ='munaldaju' AND s.status='ACTIVE'",nativeQuery = true)
    Client findByUsernameAndStatus(String clientId, Status active);
}
