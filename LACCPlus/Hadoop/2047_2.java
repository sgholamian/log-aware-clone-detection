//,temp,TestStaticMapping.java,139,165,temp,TestStaticMapping.java,119,134
//,3
public class xxx {
  @Test
  public void testAddResolveNodes() throws Throwable {
    StaticMapping mapping = newInstance();
    StaticMapping.addNodeToRack("n1", "/r1");
    List<String> queryList = createQueryList();
    List<String> resolved = mapping.resolve(queryList);
    assertEquals(2, resolved.size());
    assertEquals("/r1", resolved.get(0));
    assertEquals(NetworkTopology.DEFAULT_RACK, resolved.get(1));
    // get the switch map and examine it
    Map<String, String> switchMap = mapping.getSwitchMap();
    String topology = mapping.dumpTopology();
    LOG.info(topology);
    assertEquals(topology, 1, switchMap.size());
    assertEquals(topology, "/r1", switchMap.get("n1"));
  }

};