//,temp,NfsSecondaryStorageResource.java,2446,2460,temp,LocalNfsSecondaryStorageResource.java,55,66
//,3
public class xxx {
    @Override
    synchronized public String getRootDir(String secUrl, Integer nfsVersion) {
        try {
            URI uri = new URI(secUrl);
            String dir = mountUri(uri, nfsVersion);
            return _parent + "/" + dir;
        } catch (Exception e) {
            String msg = "GetRootDir for " + secUrl + " failed due to " + e.toString();
            s_logger.error(msg, e);
            throw new CloudRuntimeException(msg);
        }
    }

};