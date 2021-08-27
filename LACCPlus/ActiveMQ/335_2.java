//,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,298,329,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,187,217
//,3
public class xxx {
    public void testTempTopic(String prod_broker_url, String cons_broker_url) throws Exception {
        Connection conn;
        Session sess;
        Destination cons_dest;
        int num_msg;

        num_msg = 5;

        LOG.debug("TESTING TEMP TOPICS " + prod_broker_url + " -> " + cons_broker_url + " (" + num_msg + " messages)");

        //
        // Connect to the bus.
        //
        conn = createConnection(cons_broker_url);
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //
        // Create the destination on which messages are being tested.
        //
        LOG.trace("Creating destination");
        cons_dest = sess.createTemporaryTopic();

        testOneDest(conn, sess, cons_dest, num_msg);

        //
        // Cleanup
        //
        sess.close();
        conn.close();
    }

};