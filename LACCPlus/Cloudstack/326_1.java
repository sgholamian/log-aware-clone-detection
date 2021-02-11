//,temp,VsmCommand.java,132,163,temp,VsmCommand.java,99,130
//,3
public class xxx {
    public static String getUpdatePortProfile(String name, SwitchPortMode mode, List<Pair<VsmCommand.OperationType, String>> params) {
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

            // Command to update the port profile with the desired configuration.
            Element config = doc.createElement("nf:config");
            config.appendChild(configPortProfileDetails(doc, name, mode, params));
            editConfig.appendChild(config);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating update port profile message : " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating update port profile message : " + e.getMessage());
            return null;
        }
    }

};