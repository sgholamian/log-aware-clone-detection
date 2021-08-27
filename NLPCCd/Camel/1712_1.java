//,temp,sample_5744.java,2,14,temp,sample_3607.java,2,13
//,3
public class xxx {
public void testLoadTestLevelDBAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
HeaderDto headerDto = new HeaderDto("test", "company", 1);
char id = 'A';


log.info("sending with id");
}
}

};