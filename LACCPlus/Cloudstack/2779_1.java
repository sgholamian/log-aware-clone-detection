//,temp,SecondaryStorageManagerImpl.java,605,670,temp,ConsoleProxyManagerImpl.java,745,793
//,3
public class xxx {
    protected Map<String, Object> createSecStorageVmInstance(long dataCenterId, SecondaryStorageVm.Role role) {
        DataStore secStore = _dataStoreMgr.getImageStore(dataCenterId);
        if (secStore == null) {
            String msg = "No secondary storage available in zone " + dataCenterId + ", cannot create secondary storage vm";
            s_logger.warn(msg);
            throw new CloudRuntimeException(msg);
        }

        long id = _secStorageVmDao.getNextInSequence(Long.class, "id");
        String name = VirtualMachineName.getSystemVmName(id, _instance, "s").intern();
        Account systemAcct = _accountMgr.getSystemAccount();

        DataCenterDeployment plan = new DataCenterDeployment(dataCenterId);
        DataCenter dc = _dcDao.findById(plan.getDataCenterId());

        NetworkVO defaultNetwork = getDefaultNetworkForCreation(dc);

        List<? extends NetworkOffering> offerings = null;
        if (_sNwMgr.isStorageIpRangeAvailable(dataCenterId)) {
            offerings = _networkModel.getSystemAccountNetworkOfferings(NetworkOffering.SystemControlNetwork, NetworkOffering.SystemManagementNetwork, NetworkOffering.SystemStorageNetwork);
        } else {
            offerings = _networkModel.getSystemAccountNetworkOfferings(NetworkOffering.SystemControlNetwork, NetworkOffering.SystemManagementNetwork);
        }
        LinkedHashMap<Network, List<? extends NicProfile>> networks = new LinkedHashMap<Network, List<? extends NicProfile>>(offerings.size() + 1);
        NicProfile defaultNic = new NicProfile();
        defaultNic.setDefaultNic(true);
        defaultNic.setDeviceId(2);
        try {
            networks.put(_networkMgr.setupNetwork(systemAcct, _networkOfferingDao.findById(defaultNetwork.getNetworkOfferingId()), plan, null, null, false).get(0),
                    new ArrayList<NicProfile>(Arrays.asList(defaultNic)));
            for (NetworkOffering offering : offerings) {
                networks.put(_networkMgr.setupNetwork(systemAcct, offering, plan, null, null, false).get(0), new ArrayList<NicProfile>());
            }
        } catch (ConcurrentOperationException e) {
            s_logger.info("Unable to setup due to concurrent operation. " + e);
            return new HashMap<String, Object>();
        }

        VMTemplateVO template = null;
        HypervisorType availableHypervisor = _resourceMgr.getAvailableHypervisor(dataCenterId);
        template = _templateDao.findSystemVMReadyTemplate(dataCenterId, availableHypervisor);
        if (template == null) {
            throw new CloudRuntimeException("Not able to find the System templates or not downloaded in zone " + dataCenterId);
        }

        ServiceOfferingVO serviceOffering = _serviceOffering;
        if (serviceOffering == null) {
            serviceOffering = _offeringDao.findDefaultSystemOffering(ServiceOffering.ssvmDefaultOffUniqueName, ConfigurationManagerImpl.SystemVMUseLocalStorage.valueIn(dataCenterId));
        }
        SecondaryStorageVmVO secStorageVm =
            new SecondaryStorageVmVO(id, serviceOffering.getId(), name, template.getId(), template.getHypervisorType(), template.getGuestOSId(), dataCenterId,
                systemAcct.getDomainId(), systemAcct.getId(), _accountMgr.getSystemUser().getId(), role, serviceOffering.isOfferHA());
        secStorageVm.setDynamicallyScalable(template.isDynamicallyScalable());
        secStorageVm = _secStorageVmDao.persist(secStorageVm);
        try {
            _itMgr.allocate(name, template, serviceOffering, networks, plan, null);
            secStorageVm = _secStorageVmDao.findById(secStorageVm.getId());
        } catch (InsufficientCapacityException e) {
            s_logger.warn("InsufficientCapacity", e);
            throw new CloudRuntimeException("Insufficient capacity exception", e);
        }

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("secStorageVmId", secStorageVm.getId());
        return context;
    }

};