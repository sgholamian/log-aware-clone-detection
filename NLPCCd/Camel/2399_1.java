//,temp,sample_1195.java,2,12,temp,sample_1196.java,2,13
//,2
public class xxx {
protected synchronized void initialize(Exchange exchange) throws XPathException, IOException {
if (!initialized.get()) {
if (configuration == null) {
configuration = new Configuration();
configuration.setStripsWhiteSpace(isStripsAllWhiteSpace() ? Whitespace.ALL : Whitespace.IGNORABLE);


log.info("created new configuration");
}
}
}

};