//,temp,DelegatedAdminTest.java,38,112,temp,ConfigTest.java,39,124
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

            if (api.getName().equals("rebootManagementServer")) {

                s_logger.info("Attempting to SSH into management server " + this.getParam().get("hostip"));
                try {
                    Connection conn = new Connection(this.getParam().get("hostip"));
                    conn.connect(null, 60000, 60000);

                    s_logger.info("SSHed successfully into management server " + this.getParam().get("hostip"));

                    boolean isAuthenticated = conn.authenticateWithPassword("root", "password");

                    if (isAuthenticated == false) {
                        s_logger.info("Authentication failed for root with password");
                        return false;
                    }

                    String restartCommand = "service cloud-management restart; service cloud-usage restart";
                    Session sess = conn.openSession();
                    s_logger.info("Executing : " + restartCommand);
                    sess.execCommand(restartCommand);
                    Thread.sleep(120000);
                    sess.close();
                    conn.close();

                } catch (Exception ex) {
                    s_logger.error(ex);
                    return false;
                }
            } else {
                //send a command
                api.sendCommand(this.getClient(), null);

                //verify the response of the command
                if ((api.getResponseType() == ResponseType.ERROR) && (api.getResponseCode() == 200) && (api.getTestCaseInfo() != null)) {
                    s_logger.error("Test case " + api.getTestCaseInfo() +
                        "failed. Command that was supposed to fail, passed. The command was sent with the following url " + api.getUrl());
                    error++;
                } else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() == 200)) {
                    //set parameters for the future use
                    if (api.setParam(this.getParam()) == false) {
                        s_logger.error("Exiting the test...Command " + api.getName() +
                            " didn't return parameters needed for the future use. The command was sent with url " + api.getUrl());
                        return false;
                    } else {
                        //verify parameters
                        if (api.verifyParam() == false) {
                            s_logger.error("Command " + api.getName() + " failed. Verification for returned parameters failed. Command was sent with url " + api.getUrl());
                            error++;
                        } else if (api.getTestCaseInfo() != null) {
                            s_logger.info("Test case " + api.getTestCaseInfo() + " passed. Command was sent with the url " + api.getUrl());
                        }
                    }
                } else if ((api.getResponseType() != ResponseType.ERROR) && (api.getResponseCode() != 200)) {
                    s_logger.error("Command " + api.getName() + " failed with an error code " + api.getResponseCode() + " . Command was sent with url  " + api.getUrl() +
                        " Required: " + api.getRequired());
                    if (api.getRequired() == true) {
                        s_logger.info("The command is required for the future use, so exiging");
                        return false;
                    }
                    error++;
                } else if (api.getTestCaseInfo() != null) {
                    s_logger.info("Test case " + api.getTestCaseInfo() + " passed. Command that was supposed to fail, failed - test passed. Command was sent with url " +
                        api.getUrl());
                }
            }
        }
        if (error != 0)
            return false;
        else
            return true;
    }

};