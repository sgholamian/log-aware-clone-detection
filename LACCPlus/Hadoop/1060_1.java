//,temp,TestRegistryRMOperations.java,149,197,temp,TestRegistryRMOperations.java,106,147
//,3
public class xxx {
  @Test
  public void testAsyncPurgeEntry() throws Throwable {

    String path = "/users/example/hbase/hbase1/";
    ServiceRecord written = buildExampleServiceEntry(
        PersistencePolicies.APPLICATION_ATTEMPT);
    written.set(YarnRegistryAttributes.YARN_ID,
        "testAsyncPurgeEntry_attempt_001");

    operations.mknode(RegistryPathUtils.parentOf(path), true);
    operations.bind(path, written, 0);

    ZKPathDumper dump = registry.dumpPath(false);

    LOG.info("Initial state {}", dump);

    DeleteCompletionCallback deletions = new DeleteCompletionCallback();
    int opcount = purge("/",
        written.get(YarnRegistryAttributes.YARN_ID, ""),
        PersistencePolicies.CONTAINER,
        RegistryAdminService.PurgePolicy.PurgeAll,
        deletions);
    assertPathExists(path);

    dump = registry.dumpPath(false);

    assertEquals("wrong no of delete operations in " + dump, 0,
        deletions.getEventCount());
    assertEquals("wrong no of delete operations in " + dump, 0, opcount);


    // now app attempt
    deletions = new DeleteCompletionCallback();
    opcount = purge("/",
        written.get(YarnRegistryAttributes.YARN_ID, ""),
        PersistencePolicies.APPLICATION_ATTEMPT,
        RegistryAdminService.PurgePolicy.PurgeAll,
        deletions);

    dump = registry.dumpPath(false);
    LOG.info("Final state {}", dump);

    assertPathNotFound(path);
    assertEquals("wrong no of delete operations in " + dump, 1,
        deletions.getEventCount());
    assertEquals("wrong no of delete operations in " + dump, 1, opcount);
    // and validate the callback event

  }

};