package com.facets.cloud.node.connect.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomNodeException extends RuntimeException {

  String description;

  public CustomNodeException(String description) {
    super(description);
    this.description = description;
  }
}
