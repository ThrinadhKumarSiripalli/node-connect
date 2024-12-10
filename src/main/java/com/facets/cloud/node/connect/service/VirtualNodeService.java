package com.facets.cloud.node.connect.service;

import com.facets.cloud.node.connect.dto.VirtualNodeConnectionDTO;
import com.facets.cloud.node.connect.dto.VirtualNodeDTO;

public interface VirtualNodeService {
  VirtualNodeConnectionDTO addVirtualNodes(VirtualNodeConnectionDTO virtualNodeConnectionDTO);

  VirtualNodeDTO getVirtualNode(String name);

  boolean deleteVirtualNode(String name);
}
