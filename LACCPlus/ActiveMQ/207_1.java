//,temp,JMSTransformationSpeedComparisonTest.java,224,246,temp,JMSTransformationSpeedComparisonTest.java,100,130
//,3
public class xxx {
    @Test
    public void testTypicalQpidJMSMessageInBoundOnly() throws Exception {

        EncodedMessage encoded = encode(createTypicalQpidJMSMessage());
        InboundTransformer inboundTransformer = getInboundTransformer();

        // Warm up
        for (int i = 0; i < WARM_CYCLES; ++i) {
            inboundTransformer.transform(encoded);
        }

        long totalDuration = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < PROFILE_CYCLES; ++i) {
            inboundTransformer.transform(encoded);
        }

        totalDuration += System.nanoTime() - startTime;

        LOG.info("[{}] Total time for {} cycles of transforms = {} ms  -> [{}]",
            transformer, PROFILE_CYCLES, TimeUnit.NANOSECONDS.toMillis(totalDuration), test.getMethodName());
    }

};