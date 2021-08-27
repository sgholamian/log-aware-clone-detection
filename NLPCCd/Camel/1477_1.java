//,temp,sample_1736.java,2,17,temp,sample_1735.java,2,14
//,3
public class xxx {
public void dummy_method(){
TransactionErrorHandler answer;
ErrorHandlerBuilder builder = (ErrorHandlerBuilder)routeContext.getRoute().getErrorHandlerBuilder();
if (builder instanceof ErrorHandlerBuilderRef) {
ErrorHandlerBuilderRef builderRef = (ErrorHandlerBuilderRef) builder;
String ref = builderRef.getRef();
if (ErrorHandlerBuilderRef.isErrorHandlerBuilderConfigured(ref)) {
builder = (ErrorHandlerBuilder)ErrorHandlerBuilderRef.lookupErrorHandlerBuilder(routeContext, ref);
}
}
if (builder != null && builder.supportTransacted()) {


log.info("the errorhandlerbuilder configured is already a transactionerrorhandlerbuilder");
}
}

};