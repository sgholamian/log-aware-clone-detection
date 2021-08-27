//,temp,sample_7422.java,2,16,temp,sample_2132.java,2,16
//,3
public class xxx {
public void dummy_method(){
Producer producer = directEndpoint.createProducer();
int nummsg = 1;
countLatch = new CountDownLatch(nummsg);
long start = System.currentTimeMillis();
producer.start();
for (int i = 0; i < nummsg; ++i) {
producer.process(exchange);
}
countLatch.await();
long stop = System.currentTimeMillis();


log.info("took milliseconds");
}

};