//,temp,TemplateManagerImpl.java,712,728,temp,CloudStackImageStoreDriverImpl.java,118,140
//,3
public class xxx {
    @Override
    public String getChecksum(DataStore store, String templatePath, String algorithm) {
        EndPoint ep = _epSelector.select(store);
        ComputeChecksumCommand cmd = new ComputeChecksumCommand(store.getTO(), templatePath, algorithm);
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        if (answer != null && answer.getResult()) {
            return answer.getDetails();
        }
        return null;
    }

};