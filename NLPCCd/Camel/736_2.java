//,temp,sample_3746.java,2,11,temp,sample_7693.java,2,11
//,3
public class xxx {
public void testUsingJavaExtensions() throws Exception {
Object instance = null;
try {
instance = Class.forName("org.apache.xalan.extensions.XPathFunctionResolverImpl").newInstance();
} catch (Throwable e) {


log.info("could not find xalan on the classpath so ignoring this test case");
}
}

};