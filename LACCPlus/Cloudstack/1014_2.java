//,temp,NfsSecondaryStorageResource.java,2810,2824,temp,LocalNfsSecondaryStorageResource.java,68,93
//,3
public class xxx {
    @Override
    protected void mount(String localRootPath, String remoteDevice, URI uri, Integer nfsVersion) {
        ensureLocalRootPathExists(localRootPath, uri);

        if (mountExists(localRootPath, uri)) {
            return;
        }

        attemptMount(localRootPath, remoteDevice, uri, nfsVersion);

        // Change permissions for the mountpoint - seems to bypass authentication
        Script script = new Script(true, "chmod", _timeout, s_logger);
        script.add("777", localRootPath);
        String result = script.execute();
        if (result != null) {
            String errMsg = "Unable to set permissions for " + localRootPath + " due to " + result;
            s_logger.error(errMsg);
            throw new CloudRuntimeException(errMsg);
        }
        s_logger.debug("Successfully set 777 permission for " + localRootPath);

        // XXX: Adding the check for creation of snapshots dir here. Might have
        // to move it somewhere more logical later.
        checkForSnapshotsDir(localRootPath);
        checkForVolumesDir(localRootPath);
    }

};