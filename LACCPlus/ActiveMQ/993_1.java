//,temp,AMQ3274Test.java,72,117,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,90,148
//,3
public class xxx {
    public void testMessages(Session sess, MessageProducer req_prod, Destination resp_dest, int num_msg) throws Exception {
        MessageConsumer resp_cons;
        TextMessage msg;
        MessageClient cons_client;
        int cur;
        int tot_expected;

        resp_cons = sess.createConsumer(resp_dest);

        cons_client = new MessageClient(resp_cons, num_msg);
        cons_client.start();

        cur = 0;
        while ((cur < num_msg) && (!testError)) {
            msg = sess.createTextMessage("MSG AAAA " + cur);
            msg.setIntProperty("SEQ", 100 + cur);
            msg.setStringProperty("TEST", "TOPO");
            msg.setJMSReplyTo(resp_dest);

            if (cur == (num_msg - 1))
                msg.setBooleanProperty("end-of-response", true);

            req_prod.send(msg);

            cur++;
        }

        cons_client.waitShutdown(5000);

        if (cons_client.shutdown()) {
            LOG.debug("Consumer client shutdown complete");
        } else {
            LOG.debug("Consumer client shutdown incomplete!!!");
        }

        tot_expected = num_msg * (echoResponseFill + 1);

        if (cons_client.getNumMsgReceived() == tot_expected) {
            LOG.info("Have " + tot_expected + " messages, as-expected");
        } else {
            LOG.error("Have " + cons_client.getNumMsgReceived() + " messages; expected " + tot_expected);
            testError = true;
        }

        resp_cons.close();
    }

};