//,temp,LibvirtSetupDirectDownloadCertificateCommandWrapper.java,94,103,temp,LibvirtSetupDirectDownloadCertificateCommandWrapper.java,81,89
//,3
public class xxx {
    private void importCertificate(String tempCerFilePath, String keyStoreFile, String certificateName, String privatePassword) {
        s_logger.debug("Importing certificate from temporary file to keystore");
        String importCommandFormat = "keytool -importcert -file %s -keystore %s -alias '%s' -storepass '%s' -noprompt";
        String importCmd = String.format(importCommandFormat, tempCerFilePath, keyStoreFile, certificateName, privatePassword);
        int result = Script.runSimpleBashScriptForExitValue(importCmd);
        if (result != 0) {
            s_logger.debug("Certificate " + certificateName + " not imported as it already exist on keystore");
        }
    }

};