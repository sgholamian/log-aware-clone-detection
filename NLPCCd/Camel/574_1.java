//,temp,sample_8472.java,2,8,temp,sample_8166.java,2,8
//,3
public class xxx {
public String read(String body, @XPath("/soap:Envelope/soap:Body/foo/text()") String foo) {
this.foo = foo;
this.body = body;


log.info("read method called on");
}

};