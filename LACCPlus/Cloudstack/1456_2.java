//,temp,SecondaryStorageManagerImpl.java,890,1001,temp,ConsoleProxyManagerImpl.java,1241,1345
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Start configuring console proxy manager : " + name);
        }

        Map<String, String> configs = _configDao.getConfiguration("management-server", params);

        String value = configs.get("consoleproxy.sslEnabled");
        if (value != null && value.equalsIgnoreCase("true")) {
            _sslEnabled = true;
        }

        _consoleProxyUrlDomain = configs.get(Config.ConsoleProxyUrlDomain.key());
        if( _sslEnabled && (_consoleProxyUrlDomain == null || _consoleProxyUrlDomain.isEmpty())) {
            s_logger.warn("Empty console proxy domain, explicitly disabling SSL");
            _sslEnabled = false;
        }

        value = configs.get(Config.ConsoleProxyCapacityScanInterval.key());
        _capacityScanInterval = NumbersUtil.parseLong(value, DEFAULT_CAPACITY_SCAN_INTERVAL);

        _capacityPerProxy = NumbersUtil.parseInt(configs.get("consoleproxy.session.max"), DEFAULT_PROXY_CAPACITY);
        _standbyCapacity = NumbersUtil.parseInt(configs.get("consoleproxy.capacity.standby"), DEFAULT_STANDBY_CAPACITY);
        _proxySessionTimeoutValue = NumbersUtil.parseInt(configs.get("consoleproxy.session.timeout"), DEFAULT_PROXY_SESSION_TIMEOUT);

        value = configs.get("consoleproxy.port");
        if (value != null) {
            _consoleProxyPort = NumbersUtil.parseInt(value, ConsoleProxyManager.DEFAULT_PROXY_VNC_PORT);
        }

        value = configs.get(Config.ConsoleProxyDisableRpFilter.key());
        if (value != null && value.equalsIgnoreCase("true")) {
            _disableRpFilter = true;
        }

        value = configs.get("secondary.storage.vm");
        if (value != null && value.equalsIgnoreCase("true")) {
            _useStorageVm = true;
        }

        if (s_logger.isInfoEnabled()) {
            s_logger.info("Console proxy max session soft limit : " + _capacityPerProxy);
            s_logger.info("Console proxy standby capacity : " + _standbyCapacity);
        }

        _instance = configs.get("instance.name");
        if (_instance == null) {
            _instance = "DEFAULT";
        }

        Map<String, String> agentMgrConfigs = _configDao.getConfiguration("AgentManager", params);

        value = agentMgrConfigs.get("port");
        _mgmtPort = NumbersUtil.parseInt(value, 8250);

        _listener = new ConsoleProxyListener(new VmBasedAgentHook(_instanceDao, _hostDao, _configDao, _ksMgr, _agentMgr, _keysMgr));
        _agentMgr.registerForHostEvents(_listener, true, true, false);

        _itMgr.registerGuru(VirtualMachine.Type.ConsoleProxy, this);

        //check if there is a default service offering configured
        String cpvmSrvcOffIdStr = configs.get(Config.ConsoleProxyServiceOffering.key());
        if (cpvmSrvcOffIdStr != null) {
            _serviceOffering = _offeringDao.findByUuid(cpvmSrvcOffIdStr);
            if (_serviceOffering == null) {
                try {
                    _serviceOffering = _offeringDao.findById(Long.parseLong(cpvmSrvcOffIdStr));
                } catch (NumberFormatException ex) {
                    s_logger.debug("The system service offering specified by global config is not id, but uuid=" + cpvmSrvcOffIdStr + " for console proxy vm");
                }
            }
            if (_serviceOffering == null) {
                s_logger.warn("Can't find system service offering specified by global config, uuid=" + cpvmSrvcOffIdStr + " for console proxy vm");
            }
        }

        if (_serviceOffering == null || !_serviceOffering.isSystemUse()) {
            int ramSize = NumbersUtil.parseInt(_configDao.getValue("console.ram.size"), DEFAULT_PROXY_VM_RAMSIZE);
            int cpuFreq = NumbersUtil.parseInt(_configDao.getValue("console.cpu.mhz"), DEFAULT_PROXY_VM_CPUMHZ);
            List<ServiceOfferingVO> offerings = _offeringDao.createSystemServiceOfferings("System Offering For Console Proxy",
                    ServiceOffering.consoleProxyDefaultOffUniqueName, 1, ramSize, cpuFreq, 0, 0, false, null,
                    Storage.ProvisioningType.THIN, true, null, true, VirtualMachine.Type.ConsoleProxy, true);
            // this can sometimes happen, if DB is manually or programmatically manipulated
            if (offerings == null || offerings.size() < 2) {
                String msg = "Data integrity problem : System Offering For Console Proxy has been removed?";
                s_logger.error(msg);
                throw new ConfigurationException(msg);
            }
        }

        _loadScanner = new SystemVmLoadScanner<Long>(this);
        _loadScanner.initScan(STARTUP_DELAY, _capacityScanInterval);
        _resourceMgr.registerResourceStateAdapter(this.getClass().getSimpleName(), this);

        _staticPublicIp = _configDao.getValue("consoleproxy.static.publicIp");
        if (_staticPublicIp != null) {
            _staticPort = NumbersUtil.parseInt(_configDao.getValue("consoleproxy.static.port"), 8443);
        }

        if (s_logger.isInfoEnabled()) {
            s_logger.info("Console Proxy Manager is configured.");
        }
        return true;
    }

};