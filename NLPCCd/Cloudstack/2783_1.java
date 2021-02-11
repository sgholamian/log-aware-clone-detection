//,temp,sample_5460.java,2,19,temp,sample_7338.java,2,19
//,3
public class xxx {
public void dummy_method(){
Node fstNode = commandLst.item(i);
Element fstElmnt = (Element)fstNode;
ApiCommand api = new ApiCommand(fstElmnt, this.getParam(), this.getCommands());
api.sendCommand(this.getClient(), null);
if ((api.getResponseType() == ResponseType.ERROR) && (api.getResponseCode() == 200)) {
error++;
} else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() == 200)) {
if (api.getResponseType() == ResponseType.EMPTY) {
if (api.isEmpty() == true) {
} else {


log.info("test case failed empty response was expected command was sent with url");
}
}
}
}

};