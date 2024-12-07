package com.facets.cloud.node.connect.repository;

import com.facets.cloud.node.connect.model.ConnectionGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionGroupRepository extends JpaRepository<ConnectionGroup, Long> {
  Optional<ConnectionGroup> findByNameAndIsActive(String name, boolean isActive);

  Optional<ConnectionGroup> findByName(String name);
}
