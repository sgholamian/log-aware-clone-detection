//,temp,SecondaryStorageManagerImpl.java,605,670,temp,ConsoleProxyManagerImpl.java,745,793
//,3
public class xxx {
    protected Map<String, Object> createProxyInstance(long dataCenterId, VMTemplateVO template) throws ConcurrentOperationException {

        long id = _consoleProxyDao.getNextInSequence(Long.class, "id");
        String name = VirtualMachineName.getConsoleProxyName(id, _instance);
        DataCenterVO dc = _dcDao.findById(dataCenterId);
        Account systemAcct = _accountMgr.getSystemAccount();

        DataCenterDeployment plan = new DataCenterDeployment(dataCenterId);

        NetworkVO defaultNetwork = getDefaultNetworkForCreation(dc);

        List<? extends NetworkOffering> offerings =
            _networkModel.getSystemAccountNetworkOfferings(NetworkOffering.SystemControlNetwork, NetworkOffering.SystemManagementNetwork);
        LinkedHashMap<Network, List<? extends NicProfile>> networks = new LinkedHashMap<Network, List<? extends NicProfile>>(offerings.size() + 1);
        NicProfile defaultNic = new NicProfile();
        defaultNic.setDefaultNic(true);
        defaultNic.setDeviceId(2);

        networks.put(_networkMgr.setupNetwork(systemAcct, _networkOfferingDao.findById(defaultNetwork.getNetworkOfferingId()), plan, null, null, false).get(0),
                new ArrayList<NicProfile>(Arrays.asList(defaultNic)));

        for (NetworkOffering offering : offerings) {
            networks.put(_networkMgr.setupNetwork(systemAcct, offering, plan, null, null, false).get(0), new ArrayList<NicProfile>());
        }

        ServiceOfferingVO serviceOffering = _serviceOffering;
        if (serviceOffering == null) {
            serviceOffering = _offeringDao.findDefaultSystemOffering(ServiceOffering.consoleProxyDefaultOffUniqueName, ConfigurationManagerImpl.SystemVMUseLocalStorage.valueIn(dataCenterId));
        }
        ConsoleProxyVO proxy =
            new ConsoleProxyVO(id, serviceOffering.getId(), name, template.getId(), template.getHypervisorType(), template.getGuestOSId(), dataCenterId,
                systemAcct.getDomainId(), systemAcct.getId(), _accountMgr.getSystemUser().getId(), 0, serviceOffering.isOfferHA());
        proxy.setDynamicallyScalable(template.isDynamicallyScalable());
        proxy = _consoleProxyDao.persist(proxy);
        try {
            _itMgr.allocate(name, template, serviceOffering, networks, plan, null);
        } catch (InsufficientCapacityException e) {
            s_logger.warn("InsufficientCapacity", e);
            throw new CloudRuntimeException("Insufficient capacity exception", e);
        }

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("dc", dc);
        HostPodVO pod = _podDao.findById(proxy.getPodIdToDeployIn());
        context.put("pod", pod);
        context.put("proxyVmId", proxy.getId());

        return context;
    }

};