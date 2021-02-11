//,temp,VmwareManagerImpl.java,704,730,temp,HypervManagerImpl.java,196,222
//,3
public class xxx {
    private String getMountPoint(String storageUrl) {
        String mountPoint = null;
        synchronized (_storageMounts) {
            mountPoint = _storageMounts.get(storageUrl);
            if (mountPoint != null) {
                return mountPoint;
            }

            URI uri;
            try {
                uri = new URI(storageUrl);
            } catch (URISyntaxException e) {
                s_logger.error("Invalid storage URL format ", e);
                throw new CloudRuntimeException("Unable to create mount point due to invalid storage URL format " + storageUrl);
            }

            mountPoint = mount(File.separator + File.separator + uri.getHost() + uri.getPath(), getMountParent(),
                uri.getScheme(), uri.getQuery());
            if (mountPoint == null) {
                s_logger.error("Unable to create mount point for " + storageUrl);
                return "/mnt/sec";
            }

            _storageMounts.put(storageUrl, mountPoint);
            return mountPoint;
        }
    }

};