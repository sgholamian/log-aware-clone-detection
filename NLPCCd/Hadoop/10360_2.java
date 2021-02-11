//,temp,sample_8943.java,2,16,temp,sample_8941.java,2,16
//,3
public class xxx {
public void dummy_method(){
operations.mknode(RegistryPathUtils.parentOf(path), true);
operations.bind(path, written, 0);
ZKPathDumper dump = registry.dumpPath(false);
CuratorEventCatcher events = new CuratorEventCatcher();
String id = written.get(YarnRegistryAttributes.YARN_ID, "");
int opcount = purge("/", id, PersistencePolicies.CONTAINER, RegistryAdminService.PurgePolicy.PurgeAll, events);
assertPathExists(path);
assertEquals(0, opcount);
assertEquals("Event counter", 0, events.getCount());
opcount = purge("/", id, PersistencePolicies.APPLICATION_ATTEMPT, RegistryAdminService.PurgePolicy.PurgeAll, events);


log.info("final state");
}

};