//,temp,NettyConsumerClientModeReconnectTest.java,83,99,temp,S3ObjectRangeOperationManualIT.java,91,106
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                String awsEndpoint = "aws2-s3://mycamelbucket?operation=getObjectRange&autoCreateBucket=false";

                from("direct:getObjectRange").to(awsEndpoint).process(new Processor() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ResponseInputStream<GetObjectResponse> s3 = exchange.getIn().getBody(ResponseInputStream.class);
                        LOG.info(readInputStream(s3));

                    }
                }).to("mock:result");

            }

};