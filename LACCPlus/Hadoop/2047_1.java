//,temp,TestStaticMapping.java,139,165,temp,TestStaticMapping.java,119,134
//,3
public class xxx {
  @Test
  public void testReadNodesFromConfig() throws Throwable {
    StaticMapping mapping = newInstance();
    Configuration conf = new Configuration();
    conf.set(StaticMapping.KEY_HADOOP_CONFIGURED_NODE_MAPPING, "n1=/r1,n2=/r2");
    mapping.setConf(conf);
    //even though we have inserted elements into the list, because 
    //it is driven by the script key in the configuration, it still
    //thinks that it is single rack
    assertSingleSwitch(mapping);
    List<String> l1 = new ArrayList<String>(3);
    l1.add("n1");
    l1.add("unknown");
    l1.add("n2");
    List<String> resolved = mapping.resolve(l1);
    assertEquals(3, resolved.size());
    assertEquals("/r1", resolved.get(0));
    assertEquals(NetworkTopology.DEFAULT_RACK, resolved.get(1));
    assertEquals("/r2", resolved.get(2));

    Map<String, String> switchMap = mapping.getSwitchMap();
    String topology = mapping.dumpTopology();
    LOG.info(topology);
    assertEquals(topology, 2, switchMap.size());
    assertEquals(topology, "/r1", switchMap.get("n1"));
    assertNull(topology, switchMap.get("unknown"));
  }

};