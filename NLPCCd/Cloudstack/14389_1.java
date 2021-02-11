//,temp,sample_5459.java,2,20,temp,sample_7337.java,2,20
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < commandLst.getLength(); i++) {
Node fstNode = commandLst.item(i);
Element fstElmnt = (Element)fstNode;
ApiCommand api = new ApiCommand(fstElmnt, this.getParam(), this.getCommands());
api.sendCommand(this.getClient(), null);
if ((api.getResponseType() == ResponseType.ERROR) && (api.getResponseCode() == 200)) {
error++;
} else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() == 200)) {
if (api.getResponseType() == ResponseType.EMPTY) {
if (api.isEmpty() == true) {


log.info("test case passed");
}
}
}
}
}

};