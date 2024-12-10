package com.facets.cloud.node.connect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualNodeConnectionDTO {
  private String connectionGroupName;
  private VirtualNodeDTO virtualNodeDTO;
}
