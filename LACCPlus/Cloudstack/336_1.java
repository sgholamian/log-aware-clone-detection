//,temp,VsmCommand.java,394,425,temp,VsmCommand.java,264,295
//,3
public class xxx {
    public static String getVServiceNode(String vlanId, String ipAddr) {
        try {
            // Create the document and root element.
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            DOMImplementation domImpl = docBuilder.getDOMImplementation();
            Document doc = createDocument(domImpl);

            // Edit configuration command.
            Element editConfig = doc.createElement("nf:edit-config");
            doc.getDocumentElement().appendChild(editConfig);

            // Command to get into exec configure mode.
            Element target = doc.createElement("nf:target");
            Element running = doc.createElement("nf:running");
            target.appendChild(running);
            editConfig.appendChild(target);

            // Command to create the port profile with the desired configuration.
            Element config = doc.createElement("nf:config");
            config.appendChild(configVServiceNodeDetails(doc, vlanId, ipAddr));
            editConfig.appendChild(config);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while adding vservice node for vlan " + vlanId + ", " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while adding vservice node for vlan " + vlanId + ", " + e.getMessage());
            return null;
        }
    }

};