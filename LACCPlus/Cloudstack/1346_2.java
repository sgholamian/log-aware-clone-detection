//,temp,StorageManagerImpl.java,2227,2292,temp,StorageManagerImpl.java,2016,2111
//,3
public class xxx {
    @Override
    public ImageStore discoverImageStore(String name, String url, String providerName, Long zoneId, Map details) throws IllegalArgumentException, DiscoveryException, InvalidParameterValueException {
        DataStoreProvider storeProvider = _dataStoreProviderMgr.getDataStoreProvider(providerName);

        if (storeProvider == null) {
            storeProvider = _dataStoreProviderMgr.getDefaultImageDataStoreProvider();
            if (storeProvider == null) {
                throw new InvalidParameterValueException("can't find image store provider: " + providerName);
            }
            providerName = storeProvider.getName(); // ignored passed provider name and use default image store provider name
        }

        ScopeType scopeType = ScopeType.ZONE;
        if (zoneId == null) {
            scopeType = ScopeType.REGION;
        }

        if (name == null) {
            name = url;
        }

        ImageStoreVO imageStore = _imageStoreDao.findByName(name);
        if (imageStore != null) {
            throw new InvalidParameterValueException("The image store with name " + name + " already exists, try creating with another name");
        }

        // check if scope is supported by store provider
        if (!((ImageStoreProvider)storeProvider).isScopeSupported(scopeType)) {
            throw new InvalidParameterValueException("Image store provider " + providerName + " does not support scope " + scopeType);
        }

        // check if we have already image stores from other different providers,
        // we currently are not supporting image stores from different
        // providers co-existing
        List<ImageStoreVO> imageStores = _imageStoreDao.listImageStores();
        for (ImageStoreVO store : imageStores) {
            if (!store.getProviderName().equalsIgnoreCase(providerName)) {
                throw new InvalidParameterValueException("You can only add new image stores from the same provider " + store.getProviderName() + " already added");
            }
        }

        if (zoneId != null) {
            // Check if the zone exists in the system
            DataCenterVO zone = _dcDao.findById(zoneId);
            if (zone == null) {
                throw new InvalidParameterValueException("Can't find zone by id " + zoneId);
            }

            Account account = CallContext.current().getCallingAccount();
            if (Grouping.AllocationState.Disabled == zone.getAllocationState() && !_accountMgr.isRootAdmin(account.getId())) {
                PermissionDeniedException ex = new PermissionDeniedException("Cannot perform this operation, Zone with specified id is currently disabled");
                ex.addProxyObject(zone.getUuid(), "dcId");
                throw ex;
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("zoneId", zoneId);
        params.put("url", url);
        params.put("name", name);
        params.put("details", details);
        params.put("scope", scopeType);
        params.put("providerName", storeProvider.getName());
        params.put("role", DataStoreRole.Image);

        DataStoreLifeCycle lifeCycle = storeProvider.getDataStoreLifeCycle();

        DataStore store;
        try {
            store = lifeCycle.initialize(params);
        } catch (Exception e) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Failed to add data store: " + e.getMessage(), e);
            }
            throw new CloudRuntimeException("Failed to add data store: " + e.getMessage(), e);
        }

        if (((ImageStoreProvider)storeProvider).needDownloadSysTemplate()) {
            // trigger system vm template download
            _imageSrv.downloadBootstrapSysTemplate(store);
        } else {
            // populate template_store_ref table
            _imageSrv.addSystemVMTemplatesToSecondary(store);
            _imageSrv.handleTemplateSync(store);
        }

        // associate builtin template with zones associated with this image store
        associateCrosszoneTemplatesToZone(zoneId);

        // duplicate cache store records to region wide storage
        if (scopeType == ScopeType.REGION) {
            duplicateCacheStoreRecordsToRegionStore(store.getId());
        }

        return (ImageStore)_dataStoreMgr.getDataStore(store.getId(), DataStoreRole.Image);
    }

};