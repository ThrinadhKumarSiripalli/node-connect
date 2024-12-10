package com.facets.cloud.node.connect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class VirtualNodeConnectionDTO {
  private String connectionGroupName;
  private VirtualNodeDTO virtualNodeDTO;
}
