//,temp,NfsSecondaryStorageResource.java,2710,2721,temp,LibvirtComputingResource.java,1482,1496
//,3
public class xxx {
    private void configureSSL() {
        if (!_inSystemVM) {
            return;
        }
        Script command = new Script(_configSslScr);
        command.add("-i", _publicIp);
        command.add("-h", _hostname);
        String result = command.execute();
        if (result != null) {
            s_logger.warn("Unable to configure httpd to use ssl");
        }
    }

};