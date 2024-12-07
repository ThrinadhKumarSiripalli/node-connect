package com.facets.cloud.node.connect.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Type(type = "com.facets.cloud.node.connect.converter.LocalDateTimeAttributeConverter")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "last_updated_at", nullable = false)
  @Type(type = "com.facets.cloud.node.connect.converter.LocalDateTimeAttributeConverter")
  @LastModifiedDate
  private LocalDateTime lastUpdatedAt;

  @PrePersist
  protected void onCreate() {
    this.lastUpdatedAt = this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.lastUpdatedAt = LocalDateTime.now();
  }
}
