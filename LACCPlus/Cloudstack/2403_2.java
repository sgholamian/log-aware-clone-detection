//,temp,DownloadListener.java,176,193,temp,UploadListener.java,438,457
//,3
public class xxx {
    public void sendCommand(RequestType reqType) {
        if (getJobId() != null) {
            if (s_logger.isTraceEnabled()) {
                log("Sending progress command ", Level.TRACE);
            }
            try {
                EndPoint ep = _epSelector.select(sserver);
                if (ep == null) {
                    String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
                    s_logger.error(errMsg);
                    return;
                }
                ep.sendMessageAsync(new UploadProgressCommand(getCommand(), getJobId(), reqType), new Callback(ep.getId(), this));
            } catch (Exception e) {
                s_logger.debug("Send command failed", e);
                setDisconnected();
            }
        }

    }

};