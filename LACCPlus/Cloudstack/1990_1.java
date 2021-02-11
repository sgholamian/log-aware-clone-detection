//,temp,TestClientWithAPI.java,464,497,temp,StressTestDirectAttach.java,347,376
//,3
public class xxx {
    private static List<String> getIPs(InputStream is, boolean sourceNat) {
        List<String> returnValues = new ArrayList<String>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element rootElement = doc.getDocumentElement();
            NodeList allocatedIpAddrNodes = rootElement.getElementsByTagName("publicipaddress");
            for (int i = 0; i < allocatedIpAddrNodes.getLength(); i++) {
                Node allocatedIpAddrNode = allocatedIpAddrNodes.item(i);
                NodeList childNodes = allocatedIpAddrNode.getChildNodes();
                String ipAddress = null;
                String ipAddressId = null;
                boolean isSourceNat = false; // assume it's *not* source nat until we find otherwise
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node n = childNodes.item(j);
                    //Id is being used instead of ipaddress. Changes need to done later to ipaddress variable
                    if ("id".equals(n.getNodeName())) {
                        ipAddressId = n.getTextContent();
                    } else if ("ipaddress".equals(n.getNodeName())) {
                        ipAddress = n.getTextContent();
                    } else if ("issourcenat".equals(n.getNodeName())) {
                        isSourceNat = Boolean.parseBoolean(n.getTextContent());
                    }
                }
                if ((ipAddress != null) && isSourceNat == sourceNat) {
                    returnValues.add(ipAddressId);
                    returnValues.add(ipAddress);
                }
            }
        } catch (Exception ex) {
            s_logger.error(ex);
        }
        return returnValues;
    }

};