//,temp,TransformerRouteTest.java,166,169,temp,S3ObjectRangeOperationManualIT.java,97,103
//,3
public class xxx {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ResponseInputStream<GetObjectResponse> s3 = exchange.getIn().getBody(ResponseInputStream.class);
                        LOG.info(readInputStream(s3));

                    }

};