//,temp,AbstractCamelClusterService.java,200,214,temp,AbstractCamelClusterService.java,184,198
//,2
public class xxx {
    @Override
    public void stopView(String namespace) throws Exception {
        LockHelper.doWithWriteLockT(
                lock,
                () -> {
                    ViewHolder<T> holder = views.get(namespace);

                    if (holder != null) {
                        LOG.info("Force stop of view {}", namespace);
                        holder.stopView();
                    } else {
                        LOG.warn("Error forcing stop of view {}: it does not exist", namespace);
                    }
                });
    }

};