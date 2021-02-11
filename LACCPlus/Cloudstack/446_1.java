//,temp,NfsSecondaryStorageResource.java,1579,1602,temp,NfsSecondaryStorageResource.java,1479,1503
//,3
public class xxx {
    String swiftDelete(SwiftTO swift, String container, String object) {
        Script command = new Script("/bin/bash", s_logger);
        command.add("-c");
        command.add("/usr/bin/python /usr/local/cloud/systemvm/scripts/storage/secondary/swift -A " + swift.getUrl() + " -U " + swift.getAccount() + ":" + swift.getUserName()
        + " -K " + swift.getKey() + " delete " + container + " " + object);
        OutputInterpreter.AllLinesParser parser = new OutputInterpreter.AllLinesParser();
        String result = command.execute(parser);
        if (result != null) {
            String errMsg = "swiftDelete failed , err=" + result;
            s_logger.warn(errMsg);
            return errMsg;
        }
        if (parser.getLines() != null) {
            String[] lines = parser.getLines().split("\\n");
            for (String line : lines) {
                if (line.contains("Errno") || line.contains("failed")) {
                    String errMsg = "swiftDelete failed , err=" + parser.getLines();
                    s_logger.warn(errMsg);
                    return errMsg;
                }
            }
        }
        return null;
    }

};