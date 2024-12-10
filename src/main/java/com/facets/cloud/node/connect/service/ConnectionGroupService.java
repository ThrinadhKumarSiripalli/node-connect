package com.facets.cloud.node.connect.service;

import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;

public interface ConnectionGroupService {

  ConnectionGroupDTO addConnectionGroup(ConnectionGroupDTO connectionGroupDTO);

  ConnectionGroupDTO updateConnectionGroup(ConnectionGroupDTO connectionGroupDTO);

  Boolean activateConnectionGroup(Long id);

  Boolean deleteConnectionGroup(Long id);

  Boolean deleteConnectionGroup(String name);

  ConnectionGroupDTO getConnectionGroup(Long id);

  ConnectionGroupDTO getConnectionGroup(String name);
}
