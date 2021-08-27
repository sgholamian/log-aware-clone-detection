//,temp,sample_1920.java,2,10,temp,sample_1584.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
InputStream signature = exchange.getIn().getHeader(CryptoCmsConstants.CAMEL_CRYPTO_CMS_SIGNED_DATA, InputStream.class);
if (signature == null) {
super.process(exchange);
} else {


log.info("signed data header found");
}
}

};