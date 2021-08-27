//,temp,NonBlockingConsumerRedeliveryTest.java,263,267,temp,AMQ7077Test.java,114,119
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                TabularData slowOnes = abortPolicy.getSlowConsumers();
                LOG.info("slow ones:"  + slowOnes);
                return slowOnes.size() == 0;
            }

};