package com.facets.cloud.node.connect.repository;

import com.facets.cloud.node.connect.model.VirtualNode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualNodeRepository extends JpaRepository<VirtualNode, Long> {

  List<VirtualNode> findByNameInAndIsActive(List<String> names, boolean isActive);

  List<VirtualNode> findByNameIn(List<String> names);
}
