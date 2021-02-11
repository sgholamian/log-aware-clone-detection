//,temp,HypervDirectConnectResource.java,610,620,temp,LibvirtComputingResource.java,340,355
//,3
public class xxx {
    @Override
    public ExecutionResult createFileInVR(final String routerIp, final String path, final String filename, final String content) {
        final File permKey = new File("/root/.ssh/id_rsa.cloud");
        boolean success = true;
        String details = "Creating file in VR, with ip: " + routerIp + ", file: " + filename;
        s_logger.debug(details);

        try {
            SshHelper.scpTo(routerIp, 3922, "root", permKey, null, path, content.getBytes(), filename, null);
        } catch (final Exception e) {
            s_logger.warn("Fail to create file " + path + filename + " in VR " + routerIp, e);
            details = e.getMessage();
            success = false;
        }
        return new ExecutionResult(success, details);
    }

};