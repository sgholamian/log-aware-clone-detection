//,temp,sample_743.java,2,15,temp,sample_1987.java,2,13
//,3
public class xxx {
public static PartitionExpressionProxy createExpressionProxy(Configuration conf) {
String className = MetastoreConf.getVar(conf, ConfVars.EXPRESSION_PROXY_CLASS);
try {
Class<? extends PartitionExpressionProxy> clazz = JavaUtils.getClass(className, PartitionExpressionProxy.class);
return JavaUtils.newInstance( clazz, new Class<?>[0], new Object[0]);
} catch (MetaException e) {
if (e.getMessage().matches(".* class not found")) {
return new DefaultPartitionExpressionProxy();
}


log.info("error loading partitionexpressionproxy");
}
}

};