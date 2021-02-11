//,temp,Ovm3StoragePool.java,615,635,temp,Ovm3HypervisorSupport.java,160,181
//,3
public class xxx {
    public File getSystemVMKeyFile(String filename) {
        String keyPath = Script.findScript("", "scripts/vm/systemvm/"
                + filename);
        File keyFile = null;
        if (keyPath != null) {
            LOGGER.debug("found SshKey " + keyPath);
            keyFile = new File(keyPath);
        }
        if (keyFile == null || !keyFile.exists()) {
            String key = "client/target/generated-webapp/WEB-INF/classes/scripts/vm/systemvm/"
                    + filename;
            LOGGER.warn("findScript failed, going for generated " + key);
            keyFile = new File(key);
        }
        if (keyFile == null || !keyFile.exists()) {
            String key = "/usr/share/cloudstack-common/scripts/vm/systemvm/"
                    + filename;
            LOGGER.warn("generated key retrieval failed " + key);
            keyFile = new File(key);
        }
        return keyFile;
    }

};