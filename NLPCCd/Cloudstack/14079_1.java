//,temp,sample_113.java,2,9,temp,sample_9052.java,2,9
//,3
public class xxx {
protected String getVhdParent(final Connection conn, final String primaryStorageSRUuid, final String snapshotUuid, final Boolean isISCSI) {
final String parentUuid = hypervisorResource.callHostPlugin(conn, "cloud-plugin-storage", "getVhdParent", "primaryStorageSRUuid", primaryStorageSRUuid, "snapshotUuid", snapshotUuid, "isISCSI", isISCSI.toString());
if (parentUuid == null || parentUuid.isEmpty() || parentUuid.equalsIgnoreCase("None")) {


log.info("unable to get parent of vhd in sr");
}
}

};