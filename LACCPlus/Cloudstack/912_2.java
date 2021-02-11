//,temp,BaremetalKickStartServiceImpl.java,160,176,temp,VmwareResource.java,6470,6487
//,3
public class xxx {
    private static File fetchSystemVmKeyFile() {
        String filePath = s_relativePathSystemVmKeyFileInstallDir;
        s_logger.debug("Looking for file [" + filePath + "] in the classpath.");
        URL url = Script.class.getClassLoader().getResource(filePath);
        File keyFile = null;
        if (url != null) {
            keyFile = new File(url.getPath());
        }
        if (keyFile == null || !keyFile.exists()) {
            filePath = s_defaultPathSystemVmKeyFile;
            keyFile = new File(filePath);
            s_logger.debug("Looking for file [" + filePath + "] in the classpath.");
        }
        if (!keyFile.exists()) {
            s_logger.error("Unable to locate id_rsa.cloud in your setup at " + keyFile.toString());
        }
        return keyFile;
    }

};