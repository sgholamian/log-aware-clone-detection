//,temp,NfsSecondaryStorageResource.java,2810,2824,temp,LocalNfsSecondaryStorageResource.java,68,93
//,3
public class xxx {
    protected void mount(String localRootPath, String remoteDevice, URI uri, Integer nfsVersion) {
        s_logger.debug("mount " + uri.toString() + " on " + localRootPath + ((nfsVersion != null) ? " nfsVersion=" + nfsVersion : ""));
        ensureLocalRootPathExists(localRootPath, uri);

        if (mountExists(localRootPath, uri)) {
            return;
        }

        attemptMount(localRootPath, remoteDevice, uri, nfsVersion);

        // XXX: Adding the check for creation of snapshots dir here. Might have
        // to move it somewhere more logical later.
        checkForSnapshotsDir(localRootPath);
        checkForVolumesDir(localRootPath);
    }

};