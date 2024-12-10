package com.facets.cloud.node.connect.controller;

import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;
import com.facets.cloud.node.connect.service.ConnectionGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/connection-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionGroupController {

  private final ConnectionGroupService connectionGroupService;

  @GetMapping("/test")
  public boolean testController() {
    return Boolean.TRUE;
  }

  @PostMapping
  public ConnectionGroupDTO createConnectionGroup(
      @RequestBody ConnectionGroupDTO connectionGroupDTO) {
    return connectionGroupService.addConnectionGroup(connectionGroupDTO);
  }

  @PutMapping
  public ConnectionGroupDTO updateConnectionGroup(
      @RequestBody ConnectionGroupDTO connectionGroupDTO) {
    return connectionGroupService.updateConnectionGroup(connectionGroupDTO);
  }

  @DeleteMapping
  public Boolean deleteConnectionGroup(@RequestParam Long id) {
    return connectionGroupService.deleteConnectionGroup(id);
  }

  @DeleteMapping("/{name}")
  public Boolean deleteConnectionGroup(@PathVariable(name = "name") String name) {
    return connectionGroupService.deleteConnectionGroup(name);
  }

  @GetMapping
  public ConnectionGroupDTO getConnectionGroup(@RequestParam Long id) {
    return connectionGroupService.getConnectionGroup(id);
  }

  @GetMapping("/{name}")
  public ConnectionGroupDTO getConnectionGroup(@PathVariable(name = "name") String name) {
    return connectionGroupService.getConnectionGroup(name);
  }
}
