//,temp,BeanWithPropertiesAndHeadersInjectionTest.java,91,95,temp,BeanOgnMethodWithXPathInjectionTest.java,109,114
//,3
public class xxx {
        public void myMethod(@ExchangeProperties Map<?, ?> foo, @Headers Map<?, ?> bar) {
            this.foo = foo;
            this.bar = bar;
            LOG.info("myMethod() method called on " + this);
        }

};