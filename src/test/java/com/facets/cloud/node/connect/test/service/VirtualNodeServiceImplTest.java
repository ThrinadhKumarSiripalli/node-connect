package com.facets.cloud.node.connect.test.service;

import com.facets.cloud.node.connect.converter.VirtualNodeConverter;
import com.facets.cloud.node.connect.dto.ConnectionGroupDTO;
import com.facets.cloud.node.connect.dto.VirtualNodeConnectionDTO;
import com.facets.cloud.node.connect.dto.VirtualNodeDTO;
import com.facets.cloud.node.connect.exception.CustomNodeException;
import com.facets.cloud.node.connect.model.VirtualNode;
import com.facets.cloud.node.connect.repository.VirtualNodeRepository;
import com.facets.cloud.node.connect.service.ConnectionGroupService;
import com.facets.cloud.node.connect.service.impl.VirtualNodeServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VirtualNodeServiceImplTest {

  @Mock private VirtualNodeRepository virtualNodeRepository;

  @Mock private ConnectionGroupService connectionGroupService;

  @Mock private VirtualNodeConverter virtualNodeConverter;

  //    @Spy
  @InjectMocks private VirtualNodeServiceImpl virtualNodeService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAddVirtualNodes_Success() {
    VirtualNodeConnectionDTO dto = new VirtualNodeConnectionDTO();
    dto.setConnectionGroupName("Connection Group #1");
    VirtualNodeDTO nodeDTO = new VirtualNodeDTO();
    nodeDTO.setName("Node1");
    dto.setVirtualNodeDTO(nodeDTO);
    VirtualNode virtualNode = new VirtualNode();
    virtualNode.setId(1l);
    virtualNode.setName("Node1");
    VirtualNodeDTO virtualNodeDTO = new VirtualNodeDTO();
    virtualNodeDTO.setId(1l);
    virtualNodeDTO.setName("Node1");

    ConnectionGroupDTO groupDTO = new ConnectionGroupDTO();
    groupDTO.setId(1L);

    Mockito.when(connectionGroupService.getConnectionGroup("Connection Group #1", true))
        .thenReturn(groupDTO);
    Mockito.when(
            virtualNodeRepository.findByNameInAndIsActive(Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(new ArrayList<>());
    Mockito.when(virtualNodeConverter.convertTo(Mockito.any())).thenReturn(virtualNode);
    Mockito.when(virtualNodeRepository.save(Mockito.any())).thenReturn(virtualNode);
    Mockito.when(virtualNodeConverter.convertFrom(Mockito.any())).thenReturn(virtualNodeDTO);

    VirtualNodeConnectionDTO result = virtualNodeService.addVirtualNodes(dto);

    Assert.assertNotNull(result);
    Assert.assertEquals("Node1", result.getVirtualNodeDTO().getName());
    Mockito.verify(virtualNodeRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void testAddVirtualNodes_ExistingNode() {
    VirtualNodeConnectionDTO dto = new VirtualNodeConnectionDTO();
    dto.setConnectionGroupName("Connection Group #1");
    VirtualNodeDTO nodeDTO = new VirtualNodeDTO();
    nodeDTO.setName("Node1");
    dto.setVirtualNodeDTO(nodeDTO);

    VirtualNode existingNode = new VirtualNode();
    existingNode.setName("Node1");
    List<VirtualNode> existingNodes = Collections.singletonList(existingNode);

    Mockito.when(connectionGroupService.getConnectionGroup("Connection Group #1", true))
        .thenReturn(new ConnectionGroupDTO());
    Mockito.when(virtualNodeRepository.findByNameInAndIsActive(Mockito.anyList(), Mockito.eq(true)))
        .thenReturn(existingNodes);

    Assertions.assertThatThrownBy(() -> virtualNodeService.addVirtualNodes(dto))
        .isInstanceOf(CustomNodeException.class)
        .hasMessageContaining("Virtual Nodes already exists with the names: [Node1]");
  }

  @Test
  public void testGetVirtualNode_Success() {
    VirtualNode node = new VirtualNode();
    node.setId(1L);
    node.setName("Node1");

    VirtualNodeDTO virtualNodeDTO = new VirtualNodeDTO();
    virtualNodeDTO.setId(1L);
    virtualNodeDTO.setName("Node1");

    Mockito.when(virtualNodeRepository.findByNameAndIsActive("node1", true))
        .thenReturn(Optional.of(node));
    Mockito.when(virtualNodeConverter.convertFrom(node)).thenReturn(virtualNodeDTO);

    VirtualNodeDTO result = virtualNodeService.getVirtualNode("Node1");

    Assert.assertNotNull(result);
    Assert.assertEquals("Node1", result.getName());
  }

  @Test
  public void testGetVirtualNode_NotFound() {
    Mockito.when(virtualNodeRepository.findByNameAndIsActive("node1", true))
        .thenReturn(Optional.empty());

    Assertions.assertThatThrownBy(() -> virtualNodeService.getVirtualNode("Node1"))
        .isInstanceOf(CustomNodeException.class)
        .hasMessageContaining("no Virtual Node present with the given name: Node1");
  }

  @Test
  public void testDeleteVirtualNode_Success() {
    VirtualNode node = new VirtualNode();
    node.setId(1L);
    node.setName("Node1");

    Mockito.when(virtualNodeRepository.findByNameAndIsActive("node1", true))
        .thenReturn(Optional.of(node));
    Mockito.when(virtualNodeRepository.findByReportsToVirtualNodeIdAndIsActive(1L, true))
        .thenReturn(new ArrayList<>());

    boolean result = virtualNodeService.deleteVirtualNode("Node1");

    Assert.assertTrue(result);
    Mockito.verify(virtualNodeRepository, Mockito.times(1)).save(node);
  }

  @Test
  public void testDeleteVirtualNode_ChildNodesPresent() {
    VirtualNode node = new VirtualNode();
    node.setId(1L);
    node.setName("Node1");

    List<VirtualNode> childNodes = Collections.singletonList(new VirtualNode());

    Mockito.when(virtualNodeRepository.findByNameAndIsActive("node1", true))
        .thenReturn(Optional.of(node));
    Mockito.when(virtualNodeRepository.findByReportsToVirtualNodeIdAndIsActive(1L, true))
        .thenReturn(childNodes);

    Assertions.assertThatThrownBy(() -> virtualNodeService.deleteVirtualNode("Node1"))
        .isInstanceOf(CustomNodeException.class)
        .hasMessageContaining("There are Child Nodes attached to it cant delete.");
  }

  @Test
  public void testDeleteVirtualNode_NotFound() {
    Mockito.when(virtualNodeRepository.findByNameAndIsActive("node1", true))
        .thenReturn(Optional.empty());

    Assertions.assertThatThrownBy(() -> virtualNodeService.deleteVirtualNode("Node1"))
        .isInstanceOf(CustomNodeException.class)
        .hasMessageContaining("no Virtual Node present with the given name: Node1");
  }
}
