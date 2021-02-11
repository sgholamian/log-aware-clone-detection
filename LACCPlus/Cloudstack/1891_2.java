//,temp,TemplateServiceImpl.java,673,695,temp,VolumeServiceImpl.java,2036,2057
//,3
public class xxx {
    private Map<Long, TemplateProp> listVolume(DataStore store) {
        ListVolumeCommand cmd = new ListVolumeCommand(store.getTO(), store.getUri());
        EndPoint ep = _epSelector.select(store);
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        if (answer != null && answer.getResult()) {
            ListVolumeAnswer tanswer = (ListVolumeAnswer)answer;
            return tanswer.getTemplateInfo();
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Can not list volumes for image store " + store.getId());
            }
        }

        return null;
    }

};