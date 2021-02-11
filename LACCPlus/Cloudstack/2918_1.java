//,temp,TestClientWithAPI.java,411,430,temp,StressTestDirectAttach.java,326,345
//,2
public class xxx {
    public static Map<String, String> getSingleValueFromXML(Element rootElement, String[] tagNames) {
        Map<String, String> returnValues = new HashMap<String, String>();
        if (rootElement == null) {
            s_logger.error("Root element is null, can't get single value from xml");
            return null;
        }
        try {
            for (int i = 0; i < tagNames.length; i++) {
                NodeList targetNodes = rootElement.getElementsByTagName(tagNames[i]);
                if (targetNodes.getLength() <= 0) {
                    s_logger.error("no " + tagNames[i] + " tag in XML response...returning null");
                } else {
                    returnValues.put(tagNames[i], targetNodes.item(0).getTextContent());
                }
            }
        } catch (Exception ex) {
            s_logger.error("error processing XML", ex);
        }
        return returnValues;
    }

};