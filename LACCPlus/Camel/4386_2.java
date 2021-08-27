//,temp,SpringTransactionPolicy.java,62,118,temp,JtaTransactionPolicy.java,58,118
//,3
public class xxx {
    @Override
    public Processor wrap(Route route, Processor processor) {
        JtaTransactionErrorHandler answer;
        // the goal is to configure the error handler builder on the route as a
        // transacted error handler. If the configured builder is not transacted,
        // we replace it with a transacted one that we configure here
        // and wrap the processor in the transacted error handler as we can have
        // transacted routes that change propagation behavior,
        // eg: from A required -> B -> requiresNew C (advanced use-case)
        // if we should not support this we do not need to wrap the processor as
        // we only need one transacted error handler

        // find the existing error handler builder
        RouteDefinition routeDefinition = (RouteDefinition) route.getRoute();
        ErrorHandlerBuilder builder = (ErrorHandlerBuilder) routeDefinition.getErrorHandlerFactory();

        // check if its a ref if so then do a lookup
        if (builder instanceof ErrorHandlerBuilderRef) {
            // its a reference to a error handler so lookup the reference
            ErrorHandlerBuilderRef builderRef = (ErrorHandlerBuilderRef) builder;
            String ref = builderRef.getRef();
            // only lookup if there was explicit an error handler builder configured
            // otherwise its just the "default" that has not explicit been configured
            // and if so then we can safely replace that with our transacted error handler
            if (ErrorHandlerHelper.isErrorHandlerFactoryConfigured(ref)) {
                LOG.debug("Looking up ErrorHandlerBuilder with ref: {}", ref);
                builder = (ErrorHandlerBuilder) ErrorHandlerHelper.lookupErrorHandlerFactory(route, ref, true);
            }
        }

        JtaTransactionErrorHandlerBuilder txBuilder;
        if ((builder != null) && builder.supportTransacted()) {
            if (!(builder instanceof JtaTransactionErrorHandlerBuilder)) {
                throw new RuntimeCamelException(
                        "The given transactional error handler builder '" + builder
                                                + "' is not of type '" + JtaTransactionErrorHandlerBuilder.class.getName()
                                                + "' which is required in this environment!");
            }
            LOG.debug("The ErrorHandlerBuilder configured is a JtaTransactionErrorHandlerBuilder: {}", builder);
            txBuilder = (JtaTransactionErrorHandlerBuilder) builder.cloneBuilder();
        } else {
            LOG.debug(
                    "No or no transactional ErrorHandlerBuilder configured, will use default JtaTransactionErrorHandlerBuilder settings");
            txBuilder = new JtaTransactionErrorHandlerBuilder();
        }

        txBuilder.setTransactionPolicy(this);

        // use error handlers from the configured builder
        if (builder != null) {
            route.addErrorHandlerFactoryReference(builder, txBuilder);
        }

        answer = createTransactionErrorHandler(route, processor, txBuilder);

        // set the route to use our transacted error handler builder
        route.setErrorHandlerFactory(txBuilder);

        // return with wrapped transacted error handler
        return answer;
    }

};