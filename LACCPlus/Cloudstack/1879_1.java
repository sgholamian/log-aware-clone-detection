//,temp,VsmPortProfileResponse.java,65,98,temp,VsmPolicyMapResponse.java,59,83
//,3
public class xxx {
    protected void parseData(Node data) {
        try {
            NodeList list = ((Element)data).getElementsByTagName(s_portProfileDetails);
            if (list.getLength() > 0) {
                NodeList readOnlyList = ((Element)list.item(0)).getElementsByTagName("__readonly__");
                Element readOnly = (Element)readOnlyList.item(0);

                for (Node node = readOnly.getFirstChild(); node != null; node = node.getNextSibling()) {
                    String currentNode = node.getNodeName();
                    String value = node.getTextContent();
                    if ("port_binding".equalsIgnoreCase(currentNode)) {
                        setPortBinding(value);
                    } else if ("profile_name".equalsIgnoreCase(currentNode)) {
                        // Set the port profile name.
                        _portProfile.profileName = value;
                    } else if ("profile_cfg".equalsIgnoreCase(currentNode)) {
                        setProfileConfiguration(value);
                    } else if ("type".equalsIgnoreCase(currentNode)) {
                        setPortType(value);
                    } else if ("status".equalsIgnoreCase(currentNode)) {
                        // Has the profile been enabled.
                        if (value.equalsIgnoreCase("1")) {
                            _portProfile.status = true;
                        }
                    } else if ("max_ports".equalsIgnoreCase(currentNode)) {
                        // Has the profile been enabled.
                        _portProfile.maxPorts = Integer.parseInt(value.trim());
                    }
                }
            }
        } catch (DOMException e) {
            s_logger.error("Error parsing the response : " + e.toString());
        }
    }

};