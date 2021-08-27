//,temp,PulsarConsumerInIT.java,54,70,temp,MinioObjectRangeOperationIT.java,78,96
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                String minioEndpoint = "minio://mycamelbucket?autoCreateBucket=true";
                String minioEndpoint1 = "minio://mycamelbucket?operation=getPartialObject";

                from("direct:putObject").to(minioEndpoint);

                from("direct:getPartialObject").to(minioEndpoint1).process(exchange -> {
                    InputStream minioPartialObject = exchange.getIn().getBody(InputStream.class);
                    LOG.info(readInputStream(minioPartialObject));

                }).to("mock:result");

            }
        };
    }

};