//,temp,sample_5416.java,2,15,temp,sample_404.java,2,17
//,3
public class xxx {
private void disableExcludedCiphers(SSLEngine sslEngine) {
String[] cipherSuites = sslEngine.getEnabledCipherSuites();
ArrayList<String> defaultEnabledCipherSuites = new ArrayList<String>(Arrays.asList(cipherSuites));
Iterator iterator = excludeCiphers.iterator();
while(iterator.hasNext()) {
String cipherName = (String)iterator.next();
if(defaultEnabledCipherSuites.contains(cipherName)) {
defaultEnabledCipherSuites.remove(cipherName);


log.info("disabling cipher suite");
}
}
}

};