//,temp,BaseSSLContextParameters.java,850,855,temp,BaseSSLContextParameters.java,842,848
//,3
public class xxx {
        @Override
        protected SSLEngine engineCreateSSLEngine() {
            SSLEngine engine = this.context.createSSLEngine();
            LOG.debug("SSLEngine [{}] created from SSLContext [{}].", engine, context);
            this.configureSSLEngine(engine);
            return engine;
        }

};