//,temp,AMQ5266SingleDestTest.java,485,497,temp,AMQ5266Test.java,462,474
//,2
public class xxx {
        public boolean completed() {
            for (List<ConsumerThread> list : threads.values()) {

                for (ConsumerThread ct : list) {

                    if (ct.isAlive()) {
                        LOG.info("thread for {} is still alive.", ct.qName);
                        return false;
                    }
                }
            }
            return true;
        }

};