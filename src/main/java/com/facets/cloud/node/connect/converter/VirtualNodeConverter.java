package com.facets.cloud.node.connect.converter;

import com.facets.cloud.node.connect.dto.VirtualNodeDTO;
import com.facets.cloud.node.connect.model.VirtualNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtualNodeConverter {

  private final ObjectMapper objectMapper;

  public VirtualNode convertTo(VirtualNodeDTO virtualNodeDTO) {
    if (virtualNodeDTO == null) {
      return null;
    }
    VirtualNode virtualNode = objectMapper.convertValue(virtualNodeDTO, VirtualNode.class);
    virtualNode.setName(virtualNode.getName().toLowerCase());
    return virtualNode;
  }

  public VirtualNodeDTO convertFrom(VirtualNode virtualNode) {
    if (virtualNode == null) {
      return null;
    }
    return objectMapper.convertValue(virtualNode, VirtualNodeDTO.class);
  }
}
