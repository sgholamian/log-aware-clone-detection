//,temp,JdbcAggregateLoadConcurrentTest.java,49,56,temp,LevelDBAggregateLoadConcurrentTest.java,65,72
//,2
public class xxx {
                public Object call() throws Exception {
                    char id = KEYS[key];
                    LOG.debug("Sending {} with id {}", value, id);
                    template.sendBodyAndHeader("direct:start", value, "id", "" + id);
                    // simulate a little delay
                    Thread.sleep(3);
                    return null;
                }

};