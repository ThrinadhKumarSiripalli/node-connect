package com.facets.cloud.node.connect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionGroupDTO {

  private Long id;

  private String name;

  private Boolean isActive = Boolean.TRUE;
}
