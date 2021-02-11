//,temp,JuniperSrxResource.java,3719,3736,temp,CiscoVnmcConnectionImpl.java,1286,1304
//,3
public class xxx {
    private Document getDocument(String xml) throws ExecutionException {
        StringReader xmlReader = new StringReader("<?xml version=\"1.0\"?> \n" + xml.trim());
        InputSource xmlSource = new InputSource(xmlReader);
        Document doc = null;

        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlSource);

        } catch (Exception e) {
            s_logger.error(e);
            throw new ExecutionException(e.getMessage());
        }

        if (doc == null) {
            throw new ExecutionException("Failed to parse xml " + xml);
        } else {
            return doc;
        }
    }

};