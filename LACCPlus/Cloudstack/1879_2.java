//,temp,VsmPortProfileResponse.java,65,98,temp,VsmPolicyMapResponse.java,59,83
//,3
public class xxx {
    protected void parseData(Node data) {
        try {
            NodeList list = ((Element)data).getElementsByTagName(s_policyMapDetails);
            if (list.getLength() > 0) {
                NodeList readOnlyList = ((Element)list.item(0)).getElementsByTagName("__readonly__");
                Element readOnly = (Element)readOnlyList.item(0);

                for (Node node = readOnly.getFirstChild(); node != null; node = node.getNextSibling()) {
                    String currentNode = node.getNodeName();
                    String value = node.getTextContent();
                    if ("pmap-name-out".equalsIgnoreCase(currentNode)) {
                        _policyMap.policyMapName = value;
                    } else if ("cir".equalsIgnoreCase(currentNode)) {
                        _policyMap.committedRate = Integer.parseInt(value.trim());
                    } else if ("bc".equalsIgnoreCase(currentNode)) {
                        _policyMap.burstRate = Integer.parseInt(value.trim());
                    } else if ("pir".equalsIgnoreCase(currentNode)) {
                        _policyMap.peakRate = Integer.parseInt(value.trim());
                    }
                }
            }
        } catch (DOMException e) {
            s_logger.error("Error parsing the response : " + e.toString());
        }
    }

};