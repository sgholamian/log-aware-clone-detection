//,temp,BaseSSLContextParameters.java,850,855,temp,BaseSSLContextParameters.java,842,848
//,3
public class xxx {
        @Override
        protected SSLEngine engineCreateSSLEngine(String peerHost, int peerPort) {
            SSLEngine engine = this.context.createSSLEngine(peerHost, peerPort);
            LOG.debug("SSLEngine [{}] created from SSLContext [{}].", engine, context);
            return this.configureSSLEngine(engine);
        }

};