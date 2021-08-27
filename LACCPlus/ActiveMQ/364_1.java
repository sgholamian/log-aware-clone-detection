//,temp,AMQ3274Test.java,183,211,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,261,293
//,3
public class xxx {
    public void testTopic(String prod_broker_url, String cons_broker_url) throws Exception {
        int num_msg;

        Connection conn;
        Session sess;
        String topic_name;

        Destination cons_dest;

        num_msg = 5;

        LOG.info("TESTING TOPICS " + prod_broker_url + " -> " + cons_broker_url + " (" + num_msg + " messages)");

        conn = createConnection(cons_broker_url);
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        topic_name = "topotest2.perm.topic";
        LOG.trace("Removing existing Topic");
        removeTopic(conn, topic_name);
        LOG.trace("Creating Topic, " + topic_name);
        cons_dest = sess.createTopic(topic_name);

        testOneDest(conn, sess, cons_dest, prod_broker_url, cons_broker_url, num_msg);

        removeTopic(conn, topic_name);
        sess.close();
        conn.close();
    }

};