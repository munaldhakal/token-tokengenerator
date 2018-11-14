package com.enfiny.tokens.tokengenerator.repository;

import com.enfiny.tokens.tokengenerator.model.GrantAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantAccessRepository extends JpaRepository<GrantAccess,Long> {
}
