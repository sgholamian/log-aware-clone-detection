//,temp,CitrixResourceBase.java,1100,1120,temp,XenServerStorageProcessor.java,587,610
//,3
public class xxx {
    private String copy_vhd_from_secondarystorage(final Connection conn, final String mountpoint, final String sruuid, final int wait) {
        final String nameLabel = "cloud-" + UUID.randomUUID().toString();
        final String results =
                hypervisorResource.callHostPluginAsync(conn, "vmopspremium", "copy_vhd_from_secondarystorage", wait, "mountpoint", mountpoint, "sruuid", sruuid, "namelabel",
                        nameLabel);
        String errMsg = null;
        if (results == null || results.isEmpty()) {
            errMsg = "copy_vhd_from_secondarystorage return null";
        } else {
            final String[] tmp = results.split("#");
            final String status = tmp[0];
            if (status.equals("0")) {
                return tmp[1];
            } else {
                errMsg = tmp[1];
            }
        }
        final String source = mountpoint.substring(mountpoint.lastIndexOf('/') + 1);
        if (hypervisorResource.killCopyProcess(conn, source)) {
            destroyVDIbyNameLabel(conn, nameLabel);
        }
        s_logger.warn(errMsg);
        throw new CloudRuntimeException(errMsg);
    }

};