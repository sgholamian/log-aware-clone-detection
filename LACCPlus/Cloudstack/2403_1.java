//,temp,DownloadListener.java,176,193,temp,UploadListener.java,438,457
//,3
public class xxx {
    public void sendCommand(RequestType reqType) {
        if (getJobId() != null) {
            if (s_logger.isTraceEnabled()) {
                log("Sending progress command ", Level.TRACE);
            }
            try {
                DownloadProgressCommand dcmd = new DownloadProgressCommand(getCommand(), getJobId(), reqType);
                if (object.getType() == DataObjectType.VOLUME) {
                    dcmd.setResourceType(ResourceType.VOLUME);
                }
                _ssAgent.sendMessageAsync(dcmd, new UploadListener.Callback(_ssAgent.getId(), this));
            } catch (Exception e) {
                s_logger.debug("Send command failed", e);
                setDisconnected();
            }
        }

    }

};