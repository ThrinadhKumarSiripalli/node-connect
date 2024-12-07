package com.facets.cloud.node.connect.converter;

import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;
import com.facets.cloud.node.connect.model.ConnectionGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionGroupConverter {

  private final ObjectMapper objectMapper;

  public ConnectionGroup convertTo(ConnectionGroupDTO connectionGroupDTO) {
    if (connectionGroupDTO == null) {
      return null;
    }
    return objectMapper.convertValue(connectionGroupDTO, ConnectionGroup.class);
  }

  public ConnectionGroupDTO convertFrom(ConnectionGroup connectionGroup) {
    if (connectionGroup == null) {
      return null;
    }
    return objectMapper.convertValue(connectionGroup, ConnectionGroupDTO.class);
  }
}
