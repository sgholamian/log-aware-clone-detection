//,temp,AMQ3274Test.java,157,178,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,222,256
//,3
public class xxx {
    public void testTempTopic(String prod_broker_url, String cons_broker_url) throws Exception {
        Connection conn;
        Session sess;
        Destination cons_dest;
        int num_msg;

        num_msg = 5;

        LOG.info("TESTING TEMP TOPICS " + prod_broker_url + " -> " + cons_broker_url + " (" + num_msg + " messages)");

        conn = createConnection(cons_broker_url);
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        LOG.trace("Creating destination");
        cons_dest = sess.createTemporaryTopic();

        testOneDest(conn, sess, cons_dest, prod_broker_url, cons_broker_url, num_msg);

        sess.close();
        conn.close();
    }

};