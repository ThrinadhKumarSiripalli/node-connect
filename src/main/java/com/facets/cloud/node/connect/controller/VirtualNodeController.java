package com.facets.cloud.node.connect.controller;

import com.facets.cloud.node.connect.dto.VirtualNodeConnectionDTO;
import com.facets.cloud.node.connect.dto.VirtualNodeDTO;
import com.facets.cloud.node.connect.service.VirtualNodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/virtual-node")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtualNodeController {

  private final VirtualNodeService virtualNodeService;

  @PostMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public VirtualNodeConnectionDTO virtualNodeAddition(
      @RequestBody VirtualNodeConnectionDTO virtualNodeConnectionDTO) {
    return virtualNodeService.addVirtualNodes(virtualNodeConnectionDTO);
  }

  @GetMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public VirtualNodeDTO getVirtualNodeDetails(@RequestParam String name) {
    return virtualNodeService.getVirtualNode(name);
  }

  @DeleteMapping(value = "", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public Boolean deleteVirtualNode(@RequestParam String name) {
    return virtualNodeService.deleteVirtualNode(name);
  }
}
