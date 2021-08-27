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
            }
        } else {
            try {
                EtcdKeysResponse response = promise.get();
                EtcdHelper.setIndex(index, response);

                serversRef.set(getServices());
            } catch (TimeoutException e) {
                LOGGER.debug("Timeout watching for {}", getConfiguration().getServicePath());
                throwable = null;
            } catch (Exception e) {
                throwable = e;
            }
        }

        if (throwable == null) {
            watch();
        } else {
            throw new RuntimeCamelException(throwable);
        }
    }

};