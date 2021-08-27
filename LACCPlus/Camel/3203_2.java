//,temp,TransformerRouteTest.java,65,69,temp,ExecDocumentationExamplesTest.java,183,187
//,3
public class xxx {
                            public void process(Exchange exchange) throws Exception {
                                InputStream outFile = exchange.getIn().getBody(InputStream.class);
                                // do something with the out file here
                                log.info(IOUtils.toString(outFile, Charset.defaultCharset()));
                            }

};