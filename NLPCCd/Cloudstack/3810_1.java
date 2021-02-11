//,temp,sample_3906.java,2,7,temp,sample_5446.java,2,7
//,2
public class xxx {
public boolean attachZone(DataStore dataStore, ZoneScope scope, HypervisorType hypervisorType) {
List<HostVO> hosts = _resourceMgr.listAllUpAndEnabledHostsInOneZoneByHypervisor(hypervisorType, scope.getScopeId());


log.info("in createpool attaching the pool to each of the hosts");
}

};