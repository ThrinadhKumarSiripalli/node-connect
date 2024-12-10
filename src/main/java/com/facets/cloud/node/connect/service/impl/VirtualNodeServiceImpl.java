package com.facets.cloud.node.connect.service.impl;

import com.facets.cloud.node.connect.converter.VirtualNodeConverter;
import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;
import com.facets.cloud.node.connect.dto.VirtualNodeConnectionDTO;
import com.facets.cloud.node.connect.dto.VirtualNodeDTO;
import com.facets.cloud.node.connect.exception.CustomNodeException;
import com.facets.cloud.node.connect.model.VirtualNode;
import com.facets.cloud.node.connect.repository.VirtualNodeRepository;
import com.facets.cloud.node.connect.service.ConnectionGroupService;
import com.facets.cloud.node.connect.service.VirtualNodeService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtualNodeServiceImpl implements VirtualNodeService {

  private final VirtualNodeRepository virtualNodeRepository;

  private final ConnectionGroupService connectionGroupService;

  private final VirtualNodeConverter virtualNodeConverter;

  @Override
  @Transactional
  public VirtualNodeConnectionDTO addVirtualNodes(
      VirtualNodeConnectionDTO virtualNodeConnectionDTO) {
    log.info("add virtual node request received with dto {}", virtualNodeConnectionDTO);
    ConnectionGroupDTO connectionGroupDTO =
        connectionGroupService.getConnectionGroup(
            virtualNodeConnectionDTO.getConnectionGroupName(), Boolean.TRUE);
    validateNodes(virtualNodeConnectionDTO);
    virtualNodeConnectionDTO.setVirtualNodeDTO(
        persistVirtualNodes(
            connectionGroupDTO.getId(), null, virtualNodeConnectionDTO.getVirtualNodeDTO()));
    return virtualNodeConnectionDTO;
  }

  private void validateNodes(VirtualNodeConnectionDTO virtualNodeConnectionDTO) {
    List<String> names = new ArrayList<>();
    names = getNames(virtualNodeConnectionDTO.getVirtualNodeDTO(), names);
    List<VirtualNode> virtualNodes =
        virtualNodeRepository.findByNameInAndIsActive(names, Boolean.TRUE);
    if (!CollectionUtils.isEmpty(virtualNodes)) {
      throw new CustomNodeException(
          "Virtual Nodes already exists with the names: "
              + virtualNodes.stream().map(VirtualNode::getName).collect(Collectors.toList()));
    }
  }

  private VirtualNodeDTO persistVirtualNodes(
      Long connectionGroupId, Long reportsToVirtualNodeId, VirtualNodeDTO virtualNodeDTO) {
    if (virtualNodeDTO == null) {
      return null;
    }
    VirtualNode virtualNode = virtualNodeConverter.convertTo(virtualNodeDTO);
    virtualNode.setConnectionGroupId(connectionGroupId);
    if (reportsToVirtualNodeId != null) {
      virtualNode.setReportsToVirtualNodeId(reportsToVirtualNodeId);
    }
    virtualNode = virtualNodeRepository.save(virtualNode);
    if (CollectionUtils.isEmpty(virtualNodeDTO.getVirtualNodeDTOList())) {
      return virtualNodeConverter.convertFrom(virtualNode);
    }
    List<VirtualNodeDTO> childVirtualNodes =
        persistVirtualNodes(
            connectionGroupId, virtualNode.getId(), virtualNodeDTO.getVirtualNodeDTOList());
    VirtualNodeDTO resultNodes = virtualNodeConverter.convertFrom(virtualNode);
    if (!CollectionUtils.isEmpty(childVirtualNodes)) {
      resultNodes.setVirtualNodeDTOList(childVirtualNodes);
    }
    return resultNodes;
  }

  private List<VirtualNodeDTO> persistVirtualNodes(
      Long connectionGroupId,
      Long reportsToVirtualNodeId,
      List<VirtualNodeDTO> virtualNodeDTOList) {
    if (CollectionUtils.isEmpty(virtualNodeDTOList)) {
      return null;
    }
    List<VirtualNodeDTO> result = new ArrayList<>();
    for (VirtualNodeDTO virtualNodeDTO : virtualNodeDTOList) {
      VirtualNodeDTO persistedNode =
          persistVirtualNodes(connectionGroupId, reportsToVirtualNodeId, virtualNodeDTO);
      if (virtualNodeDTO != null) {
        result.add(persistedNode);
      }
    }
    return result;
  }

  private List<String> getNames(VirtualNodeDTO virtualNodeDTO, List<String> names) {
    if (virtualNodeDTO == null) {
      return new ArrayList<>();
    }
    if (virtualNodeDTO.getName() == null) {
      throw new CustomNodeException("Name is Mandatory for all the virtual Nodes.");
    }
    if (Boolean.FALSE.equals(virtualNodeDTO.getIsActive())) {
      throw new CustomNodeException("Inactive Virtual Node addition is not available.");
    }
    names.add(virtualNodeDTO.getName());
    if (!CollectionUtils.isEmpty(virtualNodeDTO.getVirtualNodeDTOList())) {
      virtualNodeDTO.getVirtualNodeDTOList().forEach(node -> getNames(node, names));
    }
    return names;
  }
}
