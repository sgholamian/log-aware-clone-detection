//,temp,sample_7422.java,2,16,temp,sample_2132.java,2,16
//,3
public class xxx {
public void dummy_method(){
int nummsg = 1000;
MockEndpoint resultEndpoint = context.getEndpoint("mock:mymock", MockEndpoint.class);
resultEndpoint.expectedMessageCount(nummsg);
long start = System.currentTimeMillis();
producer.start();
for (int i = 0; i < nummsg; ++i) {
producer.process(exchange);
}
resultEndpoint.assertIsSatisfied();
long stop = System.currentTimeMillis();


log.info("took milliseconds");
}

};