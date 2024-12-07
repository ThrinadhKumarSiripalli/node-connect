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
@Table(name = "connection_group")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConnectionGroup extends AbstractEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "is_active")
  @Builder.Default
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean isActive = Boolean.TRUE;
}
