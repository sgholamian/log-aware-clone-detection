//,temp,TemplateManagerImpl.java,712,728,temp,BaseImageStoreDriverImpl.java,314,339
//,3
public class xxx {
    @Override
    public List<DatadiskTO> getDataDiskTemplates(DataObject obj) {
        List<DatadiskTO> dataDiskDetails = new ArrayList<DatadiskTO>();
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Get the data disks present in the OVA template");
        }
        DataStore store = obj.getDataStore();
        GetDatadisksCommand cmd = new GetDatadisksCommand(obj.getTO());
        EndPoint ep = _defaultEpSelector.select(store);
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        if (answer != null && answer.getResult()) {
            GetDatadisksAnswer getDatadisksAnswer = (GetDatadisksAnswer)answer;
            dataDiskDetails = getDatadisksAnswer.getDataDiskDetails(); // Details - Disk path, virtual size
        }
        else {
            throw new CloudRuntimeException("Get Data disk command failed " + answer.getDetails());
        }
        return dataDiskDetails;
    }

};