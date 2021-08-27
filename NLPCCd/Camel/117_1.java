//,temp,sample_1583.java,2,9,temp,sample_2600.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
InputStream signature = exchange.getIn().getHeader(CryptoCmsConstants.CAMEL_CRYPTO_CMS_SIGNED_DATA, InputStream.class);
if (signature == null) {


log.info("no signed data found in header assuming signed data contained in message body");
}
}

};