//,temp,JMSTransformationSpeedComparisonTest.java,248,274,temp,JMSTransformationSpeedComparisonTest.java,168,194
//,3
public class xxx {
    @Test
    public void testTypicalQpidJMSMessage() throws Exception {

        EncodedMessage encoded = encode(createTypicalQpidJMSMessage());
        InboundTransformer inboundTransformer = getInboundTransformer();
        OutboundTransformer outboundTransformer = getOutboundTransformer();

        // Warm up
        for (int i = 0; i < WARM_CYCLES; ++i) {
            ActiveMQMessage intermediate = inboundTransformer.transform(encoded);
            intermediate.onSend();
            outboundTransformer.transform(intermediate);
        }

        long totalDuration = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < PROFILE_CYCLES; ++i) {
            ActiveMQMessage intermediate = inboundTransformer.transform(encoded);
            intermediate.onSend();
            outboundTransformer.transform(intermediate);
        }
        totalDuration += System.nanoTime() - startTime;

        LOG.info("[{}] Total time for {} cycles of transforms = {} ms  -> [{}]",
            transformer, PROFILE_CYCLES, TimeUnit.NANOSECONDS.toMillis(totalDuration), test.getMethodName());
    }

};