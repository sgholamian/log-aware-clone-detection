//,temp,JMSTransformationSpeedComparisonTest.java,248,274,temp,JMSTransformationSpeedComparisonTest.java,168,194
//,3
public class xxx {
    @Test
    public void testTypicalQpidJMSMessageOutBoundOnly() throws Exception {

        EncodedMessage encoded = encode(createTypicalQpidJMSMessage());
        InboundTransformer inboundTransformer = getInboundTransformer();
        OutboundTransformer outboundTransformer = getOutboundTransformer();

        ActiveMQMessage outbound = inboundTransformer.transform(encoded);
        outbound.onSend();

        // Warm up
        for (int i = 0; i < WARM_CYCLES; ++i) {
            outboundTransformer.transform(outbound);
        }

        long totalDuration = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < PROFILE_CYCLES; ++i) {
            outboundTransformer.transform(outbound);
        }

        totalDuration += System.nanoTime() - startTime;

        LOG.info("[{}] Total time for {} cycles of transforms = {} ms  -> [{}]",
            transformer, PROFILE_CYCLES, TimeUnit.NANOSECONDS.toMillis(totalDuration), test.getMethodName());
    }

};