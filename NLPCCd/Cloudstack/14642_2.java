//,temp,sample_354.java,2,14,temp,sample_355.java,2,14
//,2
public class xxx {
public void run() {
for (int j = 0; j < proxyList.size(); j++) {
long av = 0;
if (proxyList.get(j).getConnectionsMade() != 0) {
av = proxyList.get(j).getResponseTime() / proxyList.get(j).getConnectionsMade();
}
sum = sum + av;
}
ProxyLoadTemp.end = System.currentTimeMillis();


log.info("test was running for seconds");
}

};