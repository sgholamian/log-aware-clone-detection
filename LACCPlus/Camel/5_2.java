//,temp,AbstractCamelClusterService.java,200,214,temp,AbstractCamelClusterService.java,184,198
//,2
public class xxx {
    @Override
    public void startView(String namespace) throws Exception {
        LockHelper.doWithWriteLockT(
                lock,
                () -> {
                    ViewHolder<T> holder = views.get(namespace);

                    if (holder != null) {
                        LOG.info("Force start of view {}", namespace);
                        holder.startView();
                    } else {
                        LOG.warn("Error forcing start of view {}: it does not exist", namespace);
                    }
                });
    }

};