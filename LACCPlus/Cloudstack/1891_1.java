//,temp,TemplateServiceImpl.java,673,695,temp,VolumeServiceImpl.java,2036,2057
//,3
public class xxx {
    private Map<String, TemplateProp> listTemplate(DataStore ssStore) {
        Integer nfsVersion = imageStoreDetailsUtil.getNfsVersion(ssStore.getId());
        ListTemplateCommand cmd = new ListTemplateCommand(ssStore.getTO(), nfsVersion);
        EndPoint ep = _epSelector.select(ssStore);
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        if (answer != null && answer.getResult()) {
            ListTemplateAnswer tanswer = (ListTemplateAnswer)answer;
            return tanswer.getTemplateInfo();
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("can not list template for secondary storage host " + ssStore.getId());
            }
        }

        return null;
    }

};