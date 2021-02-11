//,temp,VsmCommand.java,297,332,temp,VsmCommand.java,99,130
//,3
public class xxx {
    public static String getPortProfile(String name) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            DOMImplementation domImpl = docBuilder.getDOMImplementation();
            Document doc = createDocument(domImpl);

            Element get = doc.createElement("nf:get");
            doc.getDocumentElement().appendChild(get);

            Element filter = doc.createElement("nf:filter");
            filter.setAttribute("type", "subtree");
            get.appendChild(filter);

            // Create the show port-profile name <profile-name> command.
            Element show = doc.createElement("show");
            filter.appendChild(show);
            Element portProfile = doc.createElement("port-profile");
            show.appendChild(portProfile);
            Element nameNode = doc.createElement("name");
            portProfile.appendChild(nameNode);

            // Profile name
            Element profileName = doc.createElement("profile_name");
            profileName.setTextContent(name);
            nameNode.appendChild(profileName);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating the message to get port profile details: " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating the message to get port profile details: " + e.getMessage());
            return null;
        }
    }

};