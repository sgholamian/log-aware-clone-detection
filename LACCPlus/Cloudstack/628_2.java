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

            //verify the response of the command
            if ((api.getResponseType() == ResponseType.ERROR) && (api.getResponseCode() == 200)) {
                s_logger.error("Test case " + api.getTestCaseInfo() + " failed. Command that was supposed to fail, passed. The command was sent with the following url " +
                    api.getUrl());
                error++;
            } else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() == 200)) {
                //set parameters for the future use
                if (api.setParam(this.getParam()) == false) {
                    s_logger.error("Exiting the test...Command " + api.getName() + " didn't return parameters needed for the future use. The command was sent with url " +
                        api.getUrl());
                    return false;
                }
                //verify parameters
                if (api.verifyParam() == false) {
                    s_logger.error("Test " + api.getTestCaseInfo() + " failed. Verification for returned parameters failed. The command was sent with url " +
                        api.getUrl());
                    error++;
                } else {
                    s_logger.info("Test " + api.getTestCaseInfo() + " passed");
                }
            } else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() != 200)) {
                s_logger.error("Test case  " + api.getTestCaseInfo() + " failed with an error code " + api.getResponseCode() + " . Command was sent with url  " +
                    api.getUrl());
                if (api.getRequired() == true) {
                    s_logger.info("The command is required for the future use, so exiging");
                    return false;
                }
                error++;
            } else if (api.getTestCaseInfo() != null) {
                s_logger.info("Test case " + api.getTestCaseInfo() + " passed");

            }
        }
        if (error != 0)
            return false;
        else
            return true;
    }

};