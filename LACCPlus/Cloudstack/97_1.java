//,temp,Ovm3StoragePool.java,615,635,temp,Ovm3HypervisorSupport.java,160,181
//,3
public class xxx {
    public File getSystemVMPatchIsoFile() {
        String iso = "systemvm.iso";
        String systemVmIsoPath = Script.findScript("", "vms/" + iso);
        File isoFile = null;
        if (systemVmIsoPath != null) {
            LOGGER.debug("found systemvm patch iso " + systemVmIsoPath);
            isoFile = new File(systemVmIsoPath);
        }
        if (isoFile == null || !isoFile.exists()) {
            String svm = "client/target/generated-webapp/WEB-INF/classes/vms/"
                    + iso;
            LOGGER.debug("last resort for systemvm patch iso " + svm);
            isoFile = new File(svm);
        }
        assert isoFile != null;
        if (!isoFile.exists()) {
            LOGGER.error("Unable to locate " + iso + " in your setup at "
                    + isoFile.toString());
        }
        return isoFile;
    }

};