//,temp,NfsSecondaryStorageResource.java,2133,2151,temp,NfsSecondaryStorageResource.java,2120,2131
//,3
public class xxx {
    private String deleteLocalFile(String fullPath) {
        Script command = new Script("/bin/bash", s_logger);
        command.add("-c");
        command.add("rm -rf " + fullPath);
        String result = command.execute();
        if (result != null) {
            String errMsg = "Failed to delete file " + fullPath + ", err=" + result;
            s_logger.warn(errMsg);
            return errMsg;
        }
        return null;
    }

};