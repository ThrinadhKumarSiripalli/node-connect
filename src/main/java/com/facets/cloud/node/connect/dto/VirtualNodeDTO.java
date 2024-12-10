package com.facets.cloud.node.connect.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VirtualNodeDTO {
  private Long id;
  private String name;
  private Boolean isActive = Boolean.TRUE;
  private List<VirtualNodeDTO> virtualNodeDTOList;
  private Long reportsToVirtualNodeId;
  private Long connectionGroupId;
}
