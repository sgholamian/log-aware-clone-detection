//,temp,CitrixResourceBase.java,4571,4588,temp,Xenserver625Resource.java,80,101
//,3
public class xxx {
    @Override
    public String revertToSnapshot(final Connection conn, final VM vmSnapshot,
            final String vmName, final String oldVmUuid, final Boolean snapshotMemory, final String hostUUID)
                    throws Types.XenAPIException, XmlRpcException {

        final String results = callHostPluginAsync(conn, "vmopsSnapshot",
                "revert_memory_snapshot", 10 * 60 * 1000, "snapshotUUID",
                vmSnapshot.getUuid(conn), "vmName", vmName, "oldVmUuid",
                oldVmUuid, "snapshotMemory", snapshotMemory.toString(), "hostUUID", hostUUID);
        String errMsg = null;
        if (results == null || results.isEmpty()) {
            errMsg = "revert_memory_snapshot return null";
        } else {
            if (results.equals("0")) {
                return results;
            } else {
                errMsg = "revert_memory_snapshot exception";
            }
        }
        s_logger.warn(errMsg);
        throw new CloudRuntimeException(errMsg);
    }

};