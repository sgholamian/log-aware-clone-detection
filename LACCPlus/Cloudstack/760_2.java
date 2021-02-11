//,temp,JuniperSrxResource.java,3719,3736,temp,PaloAltoResource.java,2061,2078
//,2
public class xxx {
    private Document getDocument(String xml) throws ExecutionException {
        StringReader xmlReader = new StringReader(xml);
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