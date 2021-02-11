//,temp,VmwareManagerImpl.java,687,702,temp,BaremetalKickStartServiceImpl.java,160,176
//,3
public class xxx {
    public File getSystemVMKeyFile() {
        URL url = this.getClass().getClassLoader().getResource("scripts/vm/systemvm/id_rsa.cloud");
        File keyFile = null;
        if (url != null) {
            keyFile = new File(url.getPath());
        }
        if (keyFile == null || !keyFile.exists()) {
            keyFile = new File("/usr/share/cloudstack-common/scripts/vm/systemvm/id_rsa.cloud");
        }
        if (!keyFile.exists()) {
            throw new CloudRuntimeException(String.format("cannot find id_rsa.cloud"));
        }
        if (!keyFile.exists()) {
            s_logger.error("Unable to locate id_rsa.cloud in your setup at " + keyFile.toString());
        }
        return keyFile;
    }

};