//,temp,StockQuoteResponseProcessor.java,38,50,temp,OkResponseProcessor.java,38,47
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        LOG.info("Crafting standard response in StockQuoteResponseProcessor");
        InputStream is = getClass().getResourceAsStream("/stockquote-response.txt");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(is);
        exchange.getOut().copyFrom(exchange.getIn());
        exchange.getOut().setBody(doc);
    }

};