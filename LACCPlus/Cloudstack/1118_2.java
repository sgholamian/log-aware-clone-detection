//,temp,VsmCommand.java,198,229,temp,VsmCommand.java,165,196
//,3
public class xxx {
    public static String getDeletePortProfile(String portName) {
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
            config.appendChild(deletePortProfileDetails(doc, portName));
            editConfig.appendChild(config);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating delete port profile message : " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating delete port profile message : " + e.getMessage());
            return null;
        }
    }

};