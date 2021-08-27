//,temp,AMQ3274Test.java,244,272,temp,AMQ3274Test.java,216,239
//,3
public class xxx {
    public void testQueue(String prod_broker_url, String cons_broker_url) throws Exception {
        int num_msg;

        Connection conn;
        Session sess;
        String queue_name;

        Destination cons_dest;

        num_msg = 5;

        LOG.info("TESTING QUEUES " + prod_broker_url + " -> " + cons_broker_url + " (" + num_msg + " messages)");

        conn = createConnection(cons_broker_url);
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        queue_name = "topotest2.perm.queue";
        LOG.trace("Removing existing Queue");
        removeQueue(conn, queue_name);
        LOG.trace("Creating Queue, " + queue_name);
        cons_dest = sess.createQueue(queue_name);

        testOneDest(conn, sess, cons_dest, prod_broker_url, cons_broker_url, num_msg);

        removeQueue(conn, queue_name);
        sess.close();
        conn.close();
    }

};