package com.facets.cloud.node.connect.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
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
