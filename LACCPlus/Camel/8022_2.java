//,temp,WordpressUserProducer.java,46,50,temp,ExecDocumentationExamplesTest.java,155,164
//,3
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        // By default, the body is ExecResult instance
                        assertIsInstanceOf(ExecResult.class, exchange.getIn().getBody());
                        // Use the Camel Exec String type converter to
                        // convert the ExecResult to String
                        // In this case, the stdout is considered as output
                        String wordCountOutput = exchange.getIn().getBody(String.class);
                        // do something with the output
                        log.info(wordCountOutput);
                    }

};