//,temp,CitrixResourceBase.java,3738,3756,temp,XenServerStorageProcessor.java,1116,1134
//,3
public class xxx {
    public boolean isDeviceUsed(final Connection conn, final VM vm, final Long deviceId) {
        // Figure out the disk number to attach the VM to

        String msg = null;
        try {
            final Set<String> allowedVBDDevices = vm.getAllowedVBDDevices(conn);
            if (allowedVBDDevices.contains(deviceId.toString())) {
                return false;
            }
            return true;
        } catch (final XmlRpcException e) {
            msg = "Catch XmlRpcException due to: " + e.getMessage();
            s_logger.warn(msg, e);
        } catch (final XenAPIException e) {
            msg = "Catch XenAPIException due to: " + e.toString();
            s_logger.warn(msg, e);
        }
        throw new CloudRuntimeException("When check deviceId " + msg);
    }

};