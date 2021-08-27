//,temp,BeanWithPropertiesAndHeadersInjectionTest.java,91,95,temp,BeanOgnMethodWithXPathInjectionTest.java,109,114
//,3
public class xxx {
        public String read(String body, @XPath("/soap:Envelope/soap:Body/foo/text()") String foo) {
            this.foo = foo;
            this.body = body;
            LOG.info("read() method called on " + this);
            return foo;
        }

};