//,temp,TestRegistryRMOperations.java,149,197,temp,TestRegistryRMOperations.java,106,147
//,3
public class xxx {
  @Test
  public void testPurgeEntryCuratorCallback() throws Throwable {

    String path = "/users/example/hbase/hbase1/";
    ServiceRecord written = buildExampleServiceEntry(
        PersistencePolicies.APPLICATION_ATTEMPT);
    written.set(YarnRegistryAttributes.YARN_ID,
        "testAsyncPurgeEntry_attempt_001");

    operations.mknode(RegistryPathUtils.parentOf(path), true);
    operations.bind(path, written, 0);

    ZKPathDumper dump = registry.dumpPath(false);
    CuratorEventCatcher events = new CuratorEventCatcher();

    LOG.info("Initial state {}", dump);

    // container query
    String id = written.get(YarnRegistryAttributes.YARN_ID, "");
    int opcount = purge("/",
        id,
        PersistencePolicies.CONTAINER,
        RegistryAdminService.PurgePolicy.PurgeAll,
        events);
    assertPathExists(path);
    assertEquals(0, opcount);
    assertEquals("Event counter", 0, events.getCount());

    // now the application attempt
    opcount = purge("/",
        id,
        PersistencePolicies.APPLICATION_ATTEMPT,
        RegistryAdminService.PurgePolicy.PurgeAll,
        events);

    LOG.info("Final state {}", dump);

    assertPathNotFound(path);
    assertEquals("wrong no of delete operations in " + dump, 1, opcount);
    // and validate the callback event
    assertEquals("Event counter", 1, events.getCount());
  }

};