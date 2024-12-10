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
@ToString
public class ConnectionGroupDTO {

  private Long id;

  private String name;

  private Boolean isActive = Boolean.TRUE;
}
