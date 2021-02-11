//,temp,SecondaryStorageManagerImpl.java,890,1001,temp,ConsoleProxyManagerImpl.java,1241,1345
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Start configuring secondary storage vm manager : " + name);
        }

        Map<String, String> configs = _configDao.getConfiguration("management-server", params);

        _secStorageVmMtuSize = NumbersUtil.parseInt(configs.get("secstorage.vm.mtu.size"), DEFAULT_SS_VM_MTUSIZE);
        String useServiceVM = _configDao.getValue("secondary.storage.vm");
        boolean _useServiceVM = false;
        if ("true".equalsIgnoreCase(useServiceVM)) {
            _useServiceVM = true;
        }

        String sslcopy = _configDao.getValue("secstorage.encrypt.copy");
        if ("true".equalsIgnoreCase(sslcopy)) {
            _useSSlCopy = true;
        }

        //default to HTTP in case of missing domain
        String ssvmUrlDomain = _configDao.getValue("secstorage.ssl.cert.domain");
        if(_useSSlCopy && (ssvmUrlDomain == null || ssvmUrlDomain.isEmpty())){
            s_logger.warn("Empty secondary storage url domain, explicitly disabling SSL");
            _useSSlCopy = false;
        }

        _allowedInternalSites = _configDao.getValue("secstorage.allowed.internal.sites");

        String value = configs.get("secstorage.capacityscan.interval");
        _capacityScanInterval = NumbersUtil.parseLong(value, DEFAULT_CAPACITY_SCAN_INTERVAL);

        _instance = configs.get("instance.name");
        if (_instance == null) {
            _instance = "DEFAULT";
        }

        Map<String, String> agentMgrConfigs = _configDao.getConfiguration("AgentManager", params);

        value = agentMgrConfigs.get("port");
        _mgmtPort = NumbersUtil.parseInt(value, 8250);

        _listener = new SecondaryStorageListener(this);
        _agentMgr.registerForHostEvents(_listener, true, false, true);

        _itMgr.registerGuru(VirtualMachine.Type.SecondaryStorageVm, this);

        //check if there is a default service offering configured
        String ssvmSrvcOffIdStr = configs.get(Config.SecondaryStorageServiceOffering.key());
        if (ssvmSrvcOffIdStr != null) {
            _serviceOffering = _offeringDao.findByUuid(ssvmSrvcOffIdStr);
            if (_serviceOffering == null) {
                try {
                    _serviceOffering = _offeringDao.findById(Long.parseLong(ssvmSrvcOffIdStr));
                } catch (NumberFormatException ex) {
                    s_logger.debug("The system service offering specified by global config is not id, but uuid=" + ssvmSrvcOffIdStr + " for secondary storage vm");
                }
            }
            if (_serviceOffering == null) {
                s_logger.warn("Can't find system service offering specified by global config, uuid=" + ssvmSrvcOffIdStr + " for secondary storage vm");
            }
        }

        if (_serviceOffering == null || !_serviceOffering.isSystemUse()) {
            int ramSize = NumbersUtil.parseInt(_configDao.getValue("ssvm.ram.size"), DEFAULT_SS_VM_RAMSIZE);
            int cpuFreq = NumbersUtil.parseInt(_configDao.getValue("ssvm.cpu.mhz"), DEFAULT_SS_VM_CPUMHZ);
            List<ServiceOfferingVO> offerings = _offeringDao.createSystemServiceOfferings("System Offering For Secondary Storage VM",
                    ServiceOffering.ssvmDefaultOffUniqueName, 1, ramSize, cpuFreq, null, null, false, null,
                    Storage.ProvisioningType.THIN, true, null, true, VirtualMachine.Type.SecondaryStorageVm, true);
            // this can sometimes happen, if DB is manually or programmatically manipulated
            if (offerings == null || offerings.size() < 2) {
                String msg = "Data integrity problem : System Offering For Secondary Storage VM has been removed?";
                s_logger.error(msg);
                throw new ConfigurationException(msg);
            }
        }

        if (_useServiceVM) {
            _loadScanner = new SystemVmLoadScanner<Long>(this);
            _loadScanner.initScan(STARTUP_DELAY, _capacityScanInterval);
        }

        _httpProxy = configs.get(Config.SecStorageProxy.key());
        if (_httpProxy != null) {
            boolean valid = true;
            String errMsg = null;
            try {
                URI uri = new URI(_httpProxy);
                if (!"http".equalsIgnoreCase(uri.getScheme())) {
                    errMsg = "Only support http proxy";
                    valid = false;
                } else if (uri.getHost() == null) {
                    errMsg = "host can not be null";
                    valid = false;
                } else if (uri.getPort() == -1) {
                    _httpProxy = _httpProxy + ":3128";
                }
            } catch (URISyntaxException e) {
                errMsg = e.toString();
            } finally {
                if (!valid) {
                    s_logger.debug("ssvm http proxy " + _httpProxy + " is invalid: " + errMsg);
                    throw new ConfigurationException("ssvm http proxy " + _httpProxy + "is invalid: " + errMsg);
                }
            }
        }
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Secondary storage vm Manager is configured.");
        }
        _resourceMgr.registerResourceStateAdapter(this.getClass().getSimpleName(), this);
        return true;
    }

};