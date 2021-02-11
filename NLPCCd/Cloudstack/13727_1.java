//,temp,sample_315.java,2,18,temp,sample_316.java,2,18
//,3
public class xxx {
public void dummy_method(){
int error = 0;
Element rootElement = this.getInputFile().get(0).getDocumentElement();
NodeList commandLst = rootElement.getElementsByTagName("command");
for (int i = 0; i < commandLst.getLength(); i++) {
Node fstNode = commandLst.item(i);
Element fstElmnt = (Element)fstNode;
ApiCommand api = new ApiCommand(fstElmnt, this.getParam(), this.getCommands());
api.sendCommand(this.getClient(), null);
if (api.getResponseCode() != 200) {
error++;


log.info("the command failed");
}
}
}

};