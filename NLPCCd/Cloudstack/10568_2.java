//,temp,sample_7336.java,2,17,temp,sample_5458.java,2,17
//,3
public class xxx {
public boolean executeTest() {
int error = 0;
Element rootElement = this.getInputFile().get(0).getDocumentElement();
NodeList commandLst = rootElement.getElementsByTagName("command");
for (int i = 0; i < commandLst.getLength(); i++) {
Node fstNode = commandLst.item(i);
Element fstElmnt = (Element)fstNode;
ApiCommand api = new ApiCommand(fstElmnt, this.getParam(), this.getCommands());
api.sendCommand(this.getClient(), null);
if ((api.getResponseType() == ResponseType.ERROR) && (api.getResponseCode() == 200)) {


log.info("test case failed command that was supposed to fail passed the command was sent with the following url");
}
}
}

};