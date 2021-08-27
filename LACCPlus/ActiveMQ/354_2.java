//,temp,JMSTransformationSpeedComparisonTest.java,196,222,temp,JMSTransformationSpeedComparisonTest.java,132,166
//,3
public class xxx {
    @Test
    public void testMessageWithNoPropertiesOrAnnotations() throws Exception {

        Message message = Proton.message();

        message.setAddress("queue://test-queue");
        message.setDeliveryCount(1);
        message.setCreationTime(System.currentTimeMillis());
        message.setContentType("text/plain");
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