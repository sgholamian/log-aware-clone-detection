//,temp,ClientHammerTest.java,60,77,temp,PurgeTxnTest.java,565,580
//,3
public class xxx {
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        try {
                            String mynode = myprefix + "-" + i;
                            znodes.add(mynode);
                            zk.create(mynode, new byte[0], Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.PERSISTENT);
                        } catch (Exception e) {
                            LOG.error("Unexpected exception occurred!", e);
                        }
                        if (i == 200) {
                            doPurge.countDown();
                        }
                    }
                    finished.countDown();
                };

};