//,temp,LibvirtSetupDirectDownloadCertificateCommandWrapper.java,94,103,temp,LibvirtSetupDirectDownloadCertificateCommandWrapper.java,81,89
//,3
public class xxx {
    private String createTemporaryFile(File agentFile, String certificateName, String certificate) {
        String tempCerFilePath = String.format("%s/%s-%s",
                agentFile.getParent(), temporaryCertFilePrefix, certificateName);
        s_logger.debug("Creating temporary certificate file into: " + tempCerFilePath);
        int result = Script.runSimpleBashScriptForExitValue(String.format("echo '%s' > %s", certificate, tempCerFilePath));
        if (result != 0) {
            throw new CloudRuntimeException("Could not create the certificate file on path: " + tempCerFilePath);
        }
        return tempCerFilePath;
    }

};