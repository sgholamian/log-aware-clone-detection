//,temp,AncientDataMotionStrategy.java,303,320,temp,BaseImageStoreDriverImpl.java,314,339
//,3
public class xxx {
    protected Answer cloneVolume(DataObject template, DataObject volume) {
        CopyCommand cmd = new CopyCommand(template.getTO(), addFullCloneFlagOnVMwareDest(volume.getTO()), 0, VirtualMachineManager.ExecuteInSequence.value());
        try {
            EndPoint ep = selector.select(volume.getDataStore());
            Answer answer = null;
            if (ep == null) {
                String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
                s_logger.error(errMsg);
                answer = new Answer(cmd, false, errMsg);
            } else {
                answer = ep.sendMessage(cmd);
            }
            return answer;
        } catch (Exception e) {
            s_logger.debug("Failed to send to storage pool", e);
            throw new CloudRuntimeException("Failed to send to storage pool", e);
        }
    }

};