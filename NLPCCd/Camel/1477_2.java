//,temp,sample_1736.java,2,17,temp,sample_1735.java,2,14
//,3
public class xxx {
public Processor wrap(RouteContext routeContext, Processor processor) {
TransactionErrorHandler answer;
ErrorHandlerBuilder builder = (ErrorHandlerBuilder)routeContext.getRoute().getErrorHandlerBuilder();
if (builder instanceof ErrorHandlerBuilderRef) {
ErrorHandlerBuilderRef builderRef = (ErrorHandlerBuilderRef) builder;
String ref = builderRef.getRef();
if (ErrorHandlerBuilderRef.isErrorHandlerBuilderConfigured(ref)) {


log.info("looking up errorhandlerbuilder with ref");
}
}
}

};