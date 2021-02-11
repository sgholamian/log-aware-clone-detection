//,temp,StressTestDirectAttach.java,305,324,temp,StressTestDirectAttach.java,280,303
//,3
public class xxx {
    public static Map<String, String> getSingleValueFromXML(InputStream is, String[] tagNames) {
        Map<String, String> returnValues = new HashMap<String, String>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element rootElement = doc.getDocumentElement();

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