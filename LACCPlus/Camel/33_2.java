//,temp,BeanWithXQueryInjectionTest.java,66,70,temp,BeanWithXPathInjectionTest.java,91,95
//,2
public class xxx {
        public void read(String body, @XPath("/soap:Envelope/soap:Body/foo/text()") String foo) {
            this.foo = foo;
            this.body = body;
            LOG.info("read() method called on " + this);
        }

};