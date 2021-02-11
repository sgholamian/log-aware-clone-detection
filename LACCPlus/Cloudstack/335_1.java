//,temp,VsmCommand.java,334,365,temp,VsmCommand.java,99,130
//,3
public class xxx {
    public static String getPolicyMap(String name) {
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
            Element policyMap = doc.createElement("policy-map");
            show.appendChild(policyMap);
            Element nameNode = doc.createElement("name");
            nameNode.setTextContent(name);
            policyMap.appendChild(nameNode);

            return serialize(domImpl, doc);
        } catch (ParserConfigurationException e) {
            s_logger.error("Error while creating the message to get policy map details : " + e.getMessage());
            return null;
        } catch (DOMException e) {
            s_logger.error("Error while creating the message to get policy map details : " + e.getMessage());
            return null;
        }
    }

};