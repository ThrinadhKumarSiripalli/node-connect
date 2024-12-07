package com.facets.cloud.node.connect.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "virtual_node")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VirtualNode extends AbstractEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "reports_to_virtual_node_id")
  private Long reportsToVirtualNodeId;

  @Column(name = "connection_group_id")
  private Long connectionGroupId;

  @Column(name = "is_active")
  @Builder.Default
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean isActive = Boolean.TRUE;
}
