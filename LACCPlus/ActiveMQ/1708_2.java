//,temp,AMQ3274Test.java,244,272,temp,AMQ3274Test.java,216,239
//,3
public class xxx {
    public void testTempQueue(String prod_broker_url, String cons_broker_url) throws Exception {
        int num_msg;

        Connection conn;
        Session sess;

        Destination cons_dest;

        num_msg = 5;

        LOG.info("TESTING TEMP QUEUES " + prod_broker_url + " -> " + cons_broker_url + " (" + num_msg + " messages)");

        conn = createConnection(cons_broker_url);
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        LOG.trace("Creating destination");
        cons_dest = sess.createTemporaryQueue();

        testOneDest(conn, sess, cons_dest, prod_broker_url, cons_broker_url, num_msg);

        sess.close();
        conn.close();
    }

};