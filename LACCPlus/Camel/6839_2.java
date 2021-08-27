//,temp,EtcdWatchServiceDiscovery.java,71,103,temp,EtcdRoutePolicy.java,297,335
//,3
public class xxx {
    @Override
    public void onResponse(ResponsePromise<EtcdKeysResponse> promise) {
        if (!isRunAllowed()) {
            return;
        }

        Throwable throwable = promise.getException();
        if (throwable instanceof EtcdException) {
            EtcdException exception = (EtcdException) throwable;
            if (EtcdHelper.isOutdatedIndexException(exception)) {
                LOGGER.debug("Outdated index, key={}, cause={}", servicePath, exception.etcdCause);
                index.set(exception.index + 1);
                throwable = null;
            }
        } else {
            try {
                EtcdKeysResponse response = promise.get();
                EtcdHelper.setIndex(index, response);

                if (response.node.value == null) {
                    setLeader(tryTakeLeadership());
                } else if (!ObjectHelper.equal(serviceName, response.node.value) && leader.get()) {
                    // Looks like I've lost leadership
                    setLeader(false);
                }
            } catch (TimeoutException e) {
                LOGGER.debug("Timeout watching for {}", servicePath);
                throwable = null;
            } catch (Exception e1) {
                throwable = e1;
            }
        }

        if (throwable == null) {
            watch();
        } else {
            throw new RuntimeCamelException(throwable);
        }
    }

};