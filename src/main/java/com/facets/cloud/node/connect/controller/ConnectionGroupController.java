package com.facets.cloud.node.connect.controller;

import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;
import com.facets.cloud.node.connect.service.ConnectionGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/connection-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionGroupController {

  private final ConnectionGroupService connectionGroupService;

  @GetMapping(value = "/test", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public boolean testController() {
    return Boolean.TRUE;
  }

  @PostMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ConnectionGroupDTO createConnectionGroup(
      @RequestBody ConnectionGroupDTO connectionGroupDTO) {
    return connectionGroupService.addConnectionGroup(connectionGroupDTO);
  }

  @PutMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ConnectionGroupDTO updateConnectionGroup(
      @RequestBody ConnectionGroupDTO connectionGroupDTO) {
    return connectionGroupService.updateConnectionGroup(connectionGroupDTO);
  }

  @PutMapping(value = "/activate", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public Boolean activateConnectionGroup(@RequestParam Long id) {
    return connectionGroupService.activateConnectionGroup(id);
  }

  @DeleteMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public Boolean deleteConnectionGroup(@RequestParam Long id) {
    return connectionGroupService.deleteConnectionGroup(id);
  }

  @DeleteMapping(value = "/{name}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public Boolean deleteConnectionGroup(@PathVariable(name = "name") String name) {
    return connectionGroupService.deleteConnectionGroup(name);
  }

  @GetMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ConnectionGroupDTO getConnectionGroup(@RequestParam Long id) {
    return connectionGroupService.getConnectionGroup(id, null);
  }

  @GetMapping(value = "/{name}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ConnectionGroupDTO getConnectionGroup(@PathVariable(name = "name") String name) {
    return connectionGroupService.getConnectionGroup(name, null);
  }
}
