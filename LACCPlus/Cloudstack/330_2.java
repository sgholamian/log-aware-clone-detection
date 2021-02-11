//,temp,VsmCommand.java,165,196,temp,VsmCommand.java,99,130
//,3
public class xxx {
    public static String getAddPortProfile(String name, PortProfileType type, BindingType binding, SwitchPortMode mode, int vlanid) {
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
            config.appendChild(configPortProfileDetails(doc, name, type, binding, mode, vlanid));
            editConfig.appendChild(config);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating add port profile message : " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating add port profile message : " + e.getMessage());
            return null;
        }
    }

};