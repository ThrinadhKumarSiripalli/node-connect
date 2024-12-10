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
import java.util.Optional;
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
  private static final int MAX_DEPTH = 100;

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
        persistVirtualNodeHierarchy(
            connectionGroupDTO.getId(), null, virtualNodeConnectionDTO.getVirtualNodeDTO()));
    return virtualNodeConnectionDTO;
  }

  private void validateNodes(VirtualNodeConnectionDTO virtualNodeConnectionDTO) {
    List<String> names =
        getNames(virtualNodeConnectionDTO.getVirtualNodeDTO(), new ArrayList<>(), 0);
    List<VirtualNode> existingNodes =
        virtualNodeRepository.findByNameInAndIsActive(names, Boolean.TRUE);
    if (!CollectionUtils.isEmpty(existingNodes)) {
      throw new CustomNodeException(
          "Virtual Nodes already exists with the names: "
              + existingNodes.stream().map(VirtualNode::getName).collect(Collectors.toList()));
    }
  }

  private VirtualNodeDTO persistVirtualNodeHierarchy(
      Long connectionGroupId, Long reportsToVirtualNodeId, VirtualNodeDTO virtualNodeDTO) {
    if (virtualNodeDTO == null) {
      return null;
    }
    VirtualNode virtualNode = virtualNodeConverter.convertTo(virtualNodeDTO);
    virtualNode.setConnectionGroupId(connectionGroupId);
    if (reportsToVirtualNodeId != null) {
      virtualNode.setReportsToVirtualNodeId(reportsToVirtualNodeId);
    }
    VirtualNode savedNode = virtualNodeRepository.save(virtualNode);
    if (CollectionUtils.isEmpty(virtualNodeDTO.getVirtualNodeDTOList())) {
      return virtualNodeConverter.convertFrom(virtualNode);
    }
    List<VirtualNodeDTO> uniqueChildren =
        virtualNodeDTO.getVirtualNodeDTOList().stream().distinct().collect(Collectors.toList());
    List<VirtualNodeDTO> childNodes =
        CollectionUtils.emptyIfNull(uniqueChildren)
            .stream()
            .map(child -> persistVirtualNodeHierarchy(connectionGroupId, savedNode.getId(), child))
            .collect(Collectors.toList());
    VirtualNodeDTO resultNode = virtualNodeConverter.convertFrom(virtualNode);
    resultNode.setVirtualNodeDTOList(childNodes);
    return resultNode;
  }

  private List<String> getNames(VirtualNodeDTO virtualNodeDTO, List<String> names, int depth) {
    if (virtualNodeDTO == null) {
      return names;
    }
    if (depth > MAX_DEPTH) {
      throw new CustomNodeException("Node hierarchy is too deep. Maximum depth is " + MAX_DEPTH);
    }
    if (virtualNodeDTO.getName() == null) {
      throw new CustomNodeException("Name is Mandatory for all the virtual Nodes.");
    }
    if (Boolean.FALSE.equals(virtualNodeDTO.getIsActive())) {
      throw new CustomNodeException("Inactive Virtual Node addition is not allowed.");
    }
    if (names.contains(virtualNodeDTO.getName().toLowerCase())) {
      throw new CustomNodeException(
          "Parent Node("
              + virtualNodeDTO.getName().toLowerCase()
              + ") cannot be present in Children Nodes.");
    }
    names.add(virtualNodeDTO.getName().toLowerCase());
    if (!CollectionUtils.isEmpty(virtualNodeDTO.getVirtualNodeDTOList())) {
      virtualNodeDTO.getVirtualNodeDTOList().forEach(node -> getNames(node, names, depth + 1));
    }
    return names;
  }

  @Override
  public VirtualNodeDTO getVirtualNode(String name) {
    Optional<VirtualNode> virtualNodeOptional =
        virtualNodeRepository.findByNameAndIsActive(name.toLowerCase(), Boolean.TRUE);
    if (!virtualNodeOptional.isPresent()) {
      throw new CustomNodeException("no Virtual Node present with the given name: " + name);
    }
    return virtualNodeConverter.convertFrom(virtualNodeOptional.get());
  }

  @Override
  public boolean deleteVirtualNode(String name) {
    Optional<VirtualNode> virtualNodeOptional =
        virtualNodeRepository.findByNameAndIsActive(name.toLowerCase(), Boolean.TRUE);
    if (!virtualNodeOptional.isPresent()) {
      throw new CustomNodeException("no Virtual Node present with the given name: " + name);
    }
    VirtualNode virtualNode = virtualNodeOptional.get();
    List<VirtualNode> childNodes =
        virtualNodeRepository.findByReportsToVirtualNodeIdAndIsActive(
            virtualNode.getId(), Boolean.TRUE);
    if (!CollectionUtils.isEmpty(childNodes)) {
      throw new CustomNodeException("There are Child Nodes attached to it cant delete.");
    }
    virtualNode.setIsActive(Boolean.TRUE);
    virtualNodeRepository.save(virtualNode);
    return true;
  }
}
