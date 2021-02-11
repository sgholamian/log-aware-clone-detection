//,temp,VmwareResource.java,918,928,temp,HypervDirectConnectResource.java,610,620
//,3
public class xxx {
    @Override
    public ExecutionResult createFileInVR(final String routerIp, final String filePath, final String fileName, final String content) {
        final File keyFile = getSystemVMKeyFile();
        try {
            SshHelper.scpTo(routerIp, 3922, "root", keyFile, null, filePath, content.getBytes(Charset.forName("UTF-8")), fileName, null);
        } catch (final Exception e) {
            s_logger.warn("Fail to create file " + filePath + fileName + " in VR " + routerIp, e);
            return new ExecutionResult(false, e.getMessage());
        }
        return new ExecutionResult(true, null);
    }

};