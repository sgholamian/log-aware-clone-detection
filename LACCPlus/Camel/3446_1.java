//,temp,SubscriptionManager.java,334,358,temp,SubscriptionManager.java,317,332
//,3
public class xxx {
        public CompletableFuture<CallMethodResult> call(
                final ExpandedNodeId nodeId, final ExpandedNodeId methodId, final Variant[] inputArguments) {

            return lookupNamespace(nodeId).thenCompose(node -> {

                LOG.debug("Node   - expanded: {}, full: {}", nodeId, node);

                return lookupNamespace(methodId).thenCompose(method -> {

                    LOG.debug("Method - expanded: {}, full: {}", methodId, method);

                    final CallMethodRequest cmr = new CallMethodRequest(node, method, inputArguments);

                    return this.client.call(cmr).whenComplete((status, error) -> {
                        if (status != null) {
                            LOG.debug("Call to node={}, method={} = {}-> {}", nodeId, methodId, inputArguments, status);
                        } else {
                            LOG.debug("Failed to call", error);
                        }
                    });

                });

            });
        }

};