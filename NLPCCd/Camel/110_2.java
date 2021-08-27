//,temp,sample_3132.java,2,12,temp,sample_631.java,2,17
//,3
public class xxx {
public void dummy_method(){
contentType = exchange.getIn().getHeader(CONTENT_TYPE, String.class);
if (contentType == null) {
return stream;
}
try {
ContentType ct = new ContentType(contentType);
if (!ct.match("multipart/*")) {
return stream;
}
} catch (ParseException e) {


log.info("invalid content type ignored");
}
}

};