//,temp,JMSTransformationSpeedComparisonTest.java,224,246,temp,JMSTransformationSpeedComparisonTest.java,100,130
//,3
public class xxx {
    @Test
    public void testBodyOnlyMessage() throws Exception {

        Message message = Proton.message();

        message.setBody(new AmqpValue("String payload for AMQP message conversion performance testing."));

        EncodedMessage encoded = encode(message);
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