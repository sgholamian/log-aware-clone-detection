//,temp,sample_6451.java,2,17,temp,sample_6450.java,2,17
//,3
public class xxx {
public void dummy_method(){
_listenerMap.put(uploadVolumeObj, ul);
try {
EndPoint ep = _epSelector.select(secStore);
if (ep == null) {
String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
s_logger.error(errMsg);
return;
}
ep.sendMessageAsync(ucmd, new UploadListener.Callback(ep.getId(), ul));
} catch (Exception e) {


log.info("unable to start upload of volume from to");
}
}

};