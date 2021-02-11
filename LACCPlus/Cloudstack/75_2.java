//,temp,LibvirtPostCertificateRenewalCommandWrapper.java,35,47,temp,LibvirtComputingResource.java,1390,1401
//,3
public class xxx {
    public boolean passCmdLine(final String vmName, final String cmdLine) throws InternalErrorException {
        final Script command = new Script(_patchScriptPath, 300 * 1000, s_logger);
        String result;
        command.add("-n", vmName);
        command.add("-c", cmdLine);
        result = command.execute();
        if (result != null) {
            s_logger.error("Passing cmdline failed:" + result);
            return false;
        }
        return true;
    }

};