//,temp,SubscriptionManager.java,334,358,temp,SubscriptionManager.java,317,332
//,3
public class xxx {
        public CompletableFuture<StatusCode> write(final ExpandedNodeId nodeId, final DataValue value) {

            return lookupNamespace(nodeId).thenCompose(node -> {

                LOG.debug("Node - expanded: {}, full: {}", nodeId, node);

                return this.client.writeValue(node, value).whenComplete((status, error) -> {
                    if (status != null) {
                        LOG.debug("Write to node={} = {} -> {}", node, value, status);
                    } else {
                        LOG.debug("Failed to write", error);
                    }
                });

            });
        }

};