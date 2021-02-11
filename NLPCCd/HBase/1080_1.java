//,temp,sample_1965.java,2,16,temp,sample_1964.java,2,16
//,3
public class xxx {
public void dummy_method(){
cm.stopRunning();
cm.join();
cm.rethrowException();
for (SimpleClient client : clients) {
client.stopRunning();
client.join();
client.rethrowException();
assertTrue(client.numCalls > 10);
}
rpcClient.close();


log.info("stopping cluster");
}

};