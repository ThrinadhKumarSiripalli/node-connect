package com.facets.cloud.node.connect.service.impl;

import com.facets.cloud.node.connect.converter.ConnectionGroupConverter;
import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;
import com.facets.cloud.node.connect.exception.CustomNodeException;
import com.facets.cloud.node.connect.model.ConnectionGroup;
import com.facets.cloud.node.connect.repository.ConnectionGroupRepository;
import com.facets.cloud.node.connect.service.ConnectionGroupService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ConnectionGroupServiceImpl implements ConnectionGroupService {

  private final ConnectionGroupRepository connectionGroupRepository;

  private final ConnectionGroupConverter connectionGroupConverter;

  @Override
  public ConnectionGroupDTO addConnectionGroup(ConnectionGroupDTO connectionGroupDTO) {
    log.info("add connection group request received with dto {}", connectionGroupDTO);
    validateConnectionGroupAddition(connectionGroupDTO);
    ConnectionGroup connectionGroup = connectionGroupConverter.convertTo(connectionGroupDTO);
    connectionGroup = connectionGroupRepository.save(connectionGroup);
    return connectionGroupConverter.convertFrom(connectionGroup);
  }

  private void validateConnectionGroupAddition(ConnectionGroupDTO connectionGroupDTO) {
    if (connectionGroupDTO.getId() != null) {
      throw new CustomNodeException("Don't Provide Id while adding a new Connection Group.");
    }
    if (connectionGroupDTO.getName() == null) {
      throw new CustomNodeException("Connection Group Name is Mandatory.");
    }
    Optional<ConnectionGroup> connectionGroupOptional =
        connectionGroupRepository.findByName(connectionGroupDTO.getName().toLowerCase());
    if (connectionGroupOptional.isPresent()) {
      throw new CustomNodeException(
          "Connection Group already exists with Name: " + connectionGroupDTO.getName());
    }
  }

  @Override
  public ConnectionGroupDTO updateConnectionGroup(ConnectionGroupDTO connectionGroupDTO) {
    log.info("update connection group request received with dto {}", connectionGroupDTO);
    Optional<ConnectionGroup> connectionGroupOptional =
        connectionGroupRepository.findById(connectionGroupDTO.getId());
    validateConnectionGroupUpdation(connectionGroupDTO, connectionGroupOptional);
    ConnectionGroup connectionGroup = connectionGroupOptional.get();
    connectionGroup.setName(connectionGroupDTO.getName().toLowerCase());
    connectionGroup = connectionGroupRepository.save(connectionGroup);
    return connectionGroupConverter.convertFrom(connectionGroup);
  }

  private void validateConnectionGroupUpdation(
      ConnectionGroupDTO connectionGroupDTO, Optional<ConnectionGroup> connectionGroupOptional) {
    if (!connectionGroupOptional.isPresent()) {
      throw new CustomNodeException(
          "No Connection Group Present for this given id " + connectionGroupDTO.getId());
    }
    ConnectionGroup connectionGroup = connectionGroupOptional.get();
    if (!connectionGroup.getName().equalsIgnoreCase(connectionGroupDTO.getName())) {
      Optional<ConnectionGroup> connectionGroupWithName =
          connectionGroupRepository.findByName(connectionGroupDTO.getName().toLowerCase());
      if (connectionGroupWithName.isPresent()) {
        throw new CustomNodeException(
            "Connection Group Already Present with the given name " + connectionGroupDTO.getName());
      }
    }
    if (Boolean.FALSE.equals(connectionGroupDTO.getIsActive())) {
      throw new CustomNodeException("Cant make the Connection Group Inactive");
    }
  }

  @Override
  public Boolean activateConnectionGroup(Long id) {
    log.info("activate connection group request received with id {}", id);
    Optional<ConnectionGroup> connectionGroupOptional = connectionGroupRepository.findById(id);
    if (!connectionGroupOptional.isPresent()) {
      throw new CustomNodeException("No Connection Group Present for this given id " + id);
    }
    ConnectionGroup connectionGroup = connectionGroupOptional.get();
    connectionGroup.setIsActive(Boolean.TRUE);
    connectionGroupRepository.save(connectionGroup);
    return true;
  }

  @Override
  public Boolean deleteConnectionGroup(Long id) {
    log.info("delete connection group request received with id {}", id);
    Optional<ConnectionGroup> connectionGroupOptional = connectionGroupRepository.findById(id);
    deleteConnectionGroup(connectionGroupOptional);
    return true;
  }

  @Override
  public Boolean deleteConnectionGroup(String name) {
    log.info("delete connection group request received with name {}", name);
    Optional<ConnectionGroup> connectionGroupOptional =
        connectionGroupRepository.findByNameAndIsActive(name, Boolean.TRUE);
    deleteConnectionGroup(connectionGroupOptional);
    return true;
  }

  private void deleteConnectionGroup(Optional<ConnectionGroup> connectionGroupOptional) {
    if (!connectionGroupOptional.isPresent()) {
      throw new CustomNodeException("No Connection Group Present.");
    }
    ConnectionGroup connectionGroup = connectionGroupOptional.get();
    connectionGroup.setIsActive(Boolean.FALSE);
    connectionGroupRepository.save(connectionGroup);
  }

  @Override
  public ConnectionGroupDTO getConnectionGroup(Long id, Boolean isActive) {
    log.info("get connection group request received with id {}", id);
    Optional<ConnectionGroup> connectionGroupOptional;
    if (Boolean.TRUE.equals(isActive)) {
      connectionGroupOptional = connectionGroupRepository.findByIdAndIsActive(id, Boolean.TRUE);
    } else {
      connectionGroupOptional = connectionGroupRepository.findById(id);
    }
    if (!connectionGroupOptional.isPresent()) {
      throw new CustomNodeException("No Connection Group Exists with given id");
    }
    return connectionGroupConverter.convertFrom(connectionGroupOptional.get());
  }

  @Override
  public ConnectionGroupDTO getConnectionGroup(String name, Boolean isActive) {
    log.info("get connection group request received with name {}", name);
    Optional<ConnectionGroup> connectionGroupOptional;
    if (Boolean.TRUE.equals(isActive)) {
      connectionGroupOptional = connectionGroupRepository.findByNameAndIsActive(name, Boolean.TRUE);
    } else {
      connectionGroupOptional = connectionGroupRepository.findByName(name);
    }
    if (!connectionGroupOptional.isPresent()) {
      throw new CustomNodeException("No Connection Group Exists with given name");
    }
    return connectionGroupConverter.convertFrom(connectionGroupOptional.get());
  }
}
