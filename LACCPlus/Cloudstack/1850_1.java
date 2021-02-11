//,temp,VsmCommand.java,367,392,temp,VsmCommand.java,297,332
//,3
public class xxx {
    public static String getHello() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            DOMImplementation domImpl = docBuilder.getDOMImplementation();

            // Root elements.
            Document doc = domImpl.createDocument(s_namespace, "nc:hello", null);

            // Client capacity. We are only supporting basic capacity.
            Element capabilities = doc.createElement("nc:capabilities");
            Element capability = doc.createElement("nc:capability");
            capability.setTextContent("urn:ietf:params:xml:ns:netconf:base:1.0");

            capabilities.appendChild(capability);
            doc.getDocumentElement().appendChild(capabilities);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating hello message : " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating hello message : " + e.getMessage());
            return null;
        }
    }

};