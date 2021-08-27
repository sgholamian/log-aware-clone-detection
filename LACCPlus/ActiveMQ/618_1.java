//,temp,AMQ2927Test.java,91,122,temp,AMQ2927Test.java,56,88
//,2
public class xxx {
    public void testSendRestart() throws Exception {

        Thread.sleep(1000);

        LOG.info("sending message");

        sendMessages("BrokerA", queue, 1);

        Thread.sleep(3000);

        LOG.info("restarting broker");

        restartBroker("BrokerA");

        Thread.sleep(5000);

        LOG.info("consuming message");

        MessageConsumer consumerA = createConsumer("BrokerA", queue);
        MessageConsumer consumerB = createConsumer("BrokerB", queue);

        Thread.sleep(1000);

        MessageIdList messagesA = getConsumerMessages("BrokerA", consumerA);
        MessageIdList messagesB = getConsumerMessages("BrokerB", consumerB);

        LOG.info("consumerA = " + messagesA);
        LOG.info("consumerB = " + messagesB);

        messagesA.assertMessagesReceived(0);
        messagesB.assertMessagesReceived(1);
    }

};