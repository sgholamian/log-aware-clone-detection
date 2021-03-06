//,temp,HA.java,34,79,temp,VMApiTest.java,36,90
//,3
public class xxx {
    @Override
    public boolean executeTest() {
        int error = 0;
        Element rootElement = this.getInputFile().get(0).getDocumentElement();
        NodeList commandLst = rootElement.getElementsByTagName("command");

        //Analyze each command, send request and build the array list of api commands
        for (int i = 0; i < commandLst.getLength(); i++) {

            Node fstNode = commandLst.item(i);
            Element fstElmnt = (Element)fstNode;

            //new command
            ApiCommand api = new ApiCommand(fstElmnt, this.getParam(), this.getCommands());

            //send a command
            api.sendCommand(this.getClient(), this.getConn());

            //verify the response parameters
            if ((api.getResponseCode() != 200) && (api.getRequired() == true)) {
                s_logger.error("Exiting the test....Command " + api.getName() + " required for the future run, failed with an error code " + api.getResponseCode() +
                    ". Command was sent with the url " + api.getUrl());
                return false;
            } else if ((api.getResponseCode() != 200) && (api.getResponseType() != ResponseType.ERROR)) {
                error++;
                s_logger.error("Command " + api.getTestCaseInfo() + " failed with an error code " + api.getResponseCode() + " . Command was sent with url  " +
                    api.getUrl());
            } else if ((api.getResponseCode() == 200) && (api.getResponseType() == ResponseType.ERROR)) {
                error++;
                s_logger.error("Command " + api.getTestCaseInfo() + " which was supposed to failed, passed. The command was sent with url  " + api.getUrl());
            } else {
                //set parameters for the future use
                if (api.setParam(this.getParam()) == false) {
                    s_logger.error("Exiting the test...Command " + api.getName() + " didn't return parameters needed for the future use. Command was sent with url " +
                        api.getUrl());
                    return false;
                }
                s_logger.info("Command " + api.getTestCaseInfo() + " passed");
            }
        }

        if (error != 0)
            return false;
        else
            return true;
    }

};