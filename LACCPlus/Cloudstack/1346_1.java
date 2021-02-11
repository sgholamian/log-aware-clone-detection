//,temp,StorageManagerImpl.java,2227,2292,temp,StorageManagerImpl.java,2016,2111
//,3
public class xxx {
    @Override
    public ImageStore createSecondaryStagingStore(CreateSecondaryStagingStoreCmd cmd) {
        String providerName = cmd.getProviderName();
        DataStoreProvider storeProvider = _dataStoreProviderMgr.getDataStoreProvider(providerName);

        if (storeProvider == null) {
            storeProvider = _dataStoreProviderMgr.getDefaultCacheDataStoreProvider();
            if (storeProvider == null) {
                throw new InvalidParameterValueException("can't find cache store provider: " + providerName);
            }
        }

        Long dcId = cmd.getZoneId();

        ScopeType scopeType = null;
        String scope = cmd.getScope();
        if (scope != null) {
            try {
                scopeType = Enum.valueOf(ScopeType.class, scope.toUpperCase());

            } catch (Exception e) {
                throw new InvalidParameterValueException("invalid scope for cache store " + scope);
            }

            if (scopeType != ScopeType.ZONE) {
                throw new InvalidParameterValueException("Only zone wide cache storage is supported");
            }
        }

        if (scopeType == ScopeType.ZONE && dcId == null) {
            throw new InvalidParameterValueException("zone id can't be null, if scope is zone");
        }

        // Check if the zone exists in the system
        DataCenterVO zone = _dcDao.findById(dcId);
        if (zone == null) {
            throw new InvalidParameterValueException("Can't find zone by id " + dcId);
        }

        Account account = CallContext.current().getCallingAccount();
        if (Grouping.AllocationState.Disabled == zone.getAllocationState() && !_accountMgr.isRootAdmin(account.getId())) {
            PermissionDeniedException ex = new PermissionDeniedException("Cannot perform this operation, Zone with specified id is currently disabled");
            ex.addProxyObject(zone.getUuid(), "dcId");
            throw ex;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", dcId);
        params.put("url", cmd.getUrl());
        params.put("name", cmd.getUrl());
        params.put("details", cmd.getDetails());
        params.put("scope", scopeType);
        params.put("providerName", storeProvider.getName());
        params.put("role", DataStoreRole.ImageCache);

        DataStoreLifeCycle lifeCycle = storeProvider.getDataStoreLifeCycle();
        DataStore store = null;
        try {
            store = lifeCycle.initialize(params);
        } catch (Exception e) {
            s_logger.debug("Failed to add data store: " + e.getMessage(), e);
            throw new CloudRuntimeException("Failed to add data store: " + e.getMessage(), e);
        }

        return (ImageStore)_dataStoreMgr.getDataStore(store.getId(), DataStoreRole.ImageCache);
    }

};