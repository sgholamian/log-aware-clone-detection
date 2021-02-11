//,temp,sample_8943.java,2,16,temp,sample_8941.java,2,16
//,3
public class xxx {
public void dummy_method(){
ZKPathDumper dump = registry.dumpPath(false);
DeleteCompletionCallback deletions = new DeleteCompletionCallback();
int opcount = purge("/", written.get(YarnRegistryAttributes.YARN_ID, ""), PersistencePolicies.CONTAINER, RegistryAdminService.PurgePolicy.PurgeAll, deletions);
assertPathExists(path);
dump = registry.dumpPath(false);
assertEquals("wrong no of delete operations in " + dump, 0, deletions.getEventCount());
assertEquals("wrong no of delete operations in " + dump, 0, opcount);
deletions = new DeleteCompletionCallback();
opcount = purge("/", written.get(YarnRegistryAttributes.YARN_ID, ""), PersistencePolicies.APPLICATION_ATTEMPT, RegistryAdminService.PurgePolicy.PurgeAll, deletions);
dump = registry.dumpPath(false);


log.info("final state");
}

};