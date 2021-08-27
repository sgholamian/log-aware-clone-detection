//,temp,BeanWithHeadersAndBodyInjectionTest.java,87,91,temp,BeanWithExpressionInjectionTest.java,70,74
//,3
public class xxx {
        public void read(String body, @Simple("${header.foo}") String foo) {
            this.foo = foo;
            this.body = body;
            LOG.info("read() method called on " + this);
        }

};