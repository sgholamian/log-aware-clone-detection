//,temp,VsmCommand.java,334,365,temp,VsmCommand.java,231,262
//,3
public class xxx {
    public static String getDeletePolicyMap(String name) {
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
            config.appendChild(deletePolicyMapDetails(doc, name));
            editConfig.appendChild(config);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating delete policy map message : " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating delete policy map message : " + e.getMessage());
            return null;
        }
    }

};