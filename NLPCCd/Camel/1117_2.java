//,temp,sample_4726.java,2,17,temp,sample_4725.java,2,18
//,3
public class xxx {
public void dummy_method(){
exchange.getIn().setHeader(HEADER_ID, id);
exchange.getIn().setHeader(HEADER_KEY, key);
exchange.getIn().setHeader(HEADER_DESIGN_DOCUMENT_NAME, designDocumentName);
exchange.getIn().setHeader(HEADER_VIEWNAME, viewName);
if ("delete".equalsIgnoreCase(consumerProcessedStrategy)) {
if (log.isTraceEnabled()) {
}
client.delete(id);
} else if ("filter".equalsIgnoreCase(consumerProcessedStrategy)) {
if (log.isTraceEnabled()) {


log.info("filtering out id");
}
}
}

};