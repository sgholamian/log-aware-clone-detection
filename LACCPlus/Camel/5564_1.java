//,temp,BeanWithHeadersAndBodyInjectionTest.java,87,91,temp,BeanWithExpressionInjectionTest.java,70,74
//,3
public class xxx {
        public void myMethod(@Headers Map<String, Object> headers, Object body) {
            this.headers = headers;
            this.body = body;
            LOG.info("myMethod() method called on " + this);
        }

};