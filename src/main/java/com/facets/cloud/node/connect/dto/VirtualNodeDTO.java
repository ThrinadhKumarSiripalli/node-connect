package com.facets.cloud.node.connect.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualNodeDTO {
  private Long id;
  private String name;
  private Boolean isActive = Boolean.TRUE;
  private List<VirtualNodeDTO> virtualNodeDTOList;
  private Long reportsToVirtualNodeId;
  private Long connectionGroupId;
}
