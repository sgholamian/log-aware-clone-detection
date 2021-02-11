//,temp,PortForwardingTest.java,36,141,temp,DelegatedAdminTest.java,38,112
//,3
public class xxx {
    @Override
    public boolean executeTest() {
        int error = 0;

        for (Document eachElement : this.getInputFile()) {

            Element rootElement = eachElement.getDocumentElement();
            NodeList commandLst = rootElement.getElementsByTagName("command");

            //Analyze each command, send request and build the array list of api commands
            for (int i = 0; i < commandLst.getLength(); i++) {
                boolean verify = false;
                Node fstNode = commandLst.item(i);
                Element fstElmnt = (Element)fstNode;

                //new command
                ApiCommand api = new ApiCommand(fstElmnt, this.getParam(), this.getCommands());

                if ((eachElement.getElementsByTagName("delegated_admin_verify_part2").getLength() != 0) && !(api.getName().equals("registerUserKeys"))) {
                    if (api.getName().startsWith("list")) {

                        if (this.denyToExecute()) {
                            api.setResponseType(ResponseType.EMPTY);
                        }
                        verify = true;
                    }

                    if (this.denyToExecute()) {
                        api.setResponseType(ResponseType.ERROR);
                    }
                }

                //send a command
                api.sendCommand(this.getClient(), null);

                //verify the response of the command
                if ((verify == true) && !(api.getResponseType() == ResponseType.ERROR || api.getResponseType() == ResponseType.EMPTY)) {
                    s_logger.error("Test case " + api.getTestCaseInfo() +
                        " failed. Command that was supposed to fail, passed. The command was sent with the following url " + api.getUrl());
                    error++;
                } else if ((verify == true) && (api.getResponseType() == ResponseType.ERROR || api.getResponseType() == ResponseType.EMPTY)) {
                    s_logger.info("Test case " + api.getTestCaseInfo() + " passed");
                } else if ((api.getResponseType() == ResponseType.ERROR) && (api.getResponseCode() == 200)) {
                    s_logger.error("Test case " + api.getTestCaseInfo() +
                        " failed. Command that was supposed to fail, passed. The command was sent with the following url " + api.getUrl());
                    error++;
                } else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() == 200)) {
                    //set parameters for the future use
                    if (api.setParam(this.getParam()) == false) {
                        s_logger.error("Exiting the test...Command " + api.getName() +
                            " didn't return parameters needed for the future use. The command was sent with url " + api.getUrl());
                        return false;
                    } else if (api.getTestCaseInfo() != null) {
                        s_logger.info("Test case " + api.getTestCaseInfo() + " passed");
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
        }

        if (error != 0)
            return false;
        else
            return true;
    }

};