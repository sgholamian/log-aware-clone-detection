//,temp,JuniperSrxResource.java,3719,3736,temp,CiscoVnmcConnectionImpl.java,1286,1304
//,3
public class xxx {
    private Document getDocument(String xml) throws ExecutionException {
        StringReader srcNatRuleReader = new StringReader(xml);
        InputSource srcNatRuleSource = new InputSource(srcNatRuleReader);
        Document doc = null;

        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(srcNatRuleSource);
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