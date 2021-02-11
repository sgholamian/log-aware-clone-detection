//,temp,VmwareResource.java,918,928,temp,LibvirtComputingResource.java,340,355
//,3
public class xxx {
    @Override
    public ExecutionResult createFileInVR(String routerIp, String filePath, String fileName, String content) {
        File keyFile = getSystemVmKeyFile();
        try {
            SshHelper.scpTo(routerIp, 3922, "root", keyFile, null, filePath, content.getBytes("UTF-8"), fileName, null);
        } catch (Exception e) {
            s_logger.warn("Fail to create file " + filePath + fileName + " in VR " + routerIp, e);
            return new ExecutionResult(false, e.getMessage());
        }
        return new ExecutionResult(true, null);
    }

};