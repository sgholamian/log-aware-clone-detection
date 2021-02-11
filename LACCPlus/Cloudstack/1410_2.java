//,temp,VirtualNetworkApplianceManagerImpl.java,505,590,temp,UserVmManagerImpl.java,2039,2096
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        _name = name;

        if (_configDao == null) {
            throw new ConfigurationException("Unable to get the configuration dao.");
        }

        Map<String, String> configs = _configDao.getConfiguration("AgentManager", params);

        _instance = configs.get("instance.name");
        if (_instance == null) {
            _instance = "DEFAULT";
        }

        String workers = configs.get("expunge.workers");
        int wrks = NumbersUtil.parseInt(workers, 10);
        capacityReleaseInterval = NumbersUtil.parseInt(_configDao.getValue(Config.CapacitySkipcountingHours.key()), 3600);

        String time = configs.get("expunge.interval");
        _expungeInterval = NumbersUtil.parseInt(time, 86400);
        time = configs.get("expunge.delay");
        _expungeDelay = NumbersUtil.parseInt(time, _expungeInterval);

        _executor = Executors.newScheduledThreadPool(wrks, new NamedThreadFactory("UserVm-Scavenger"));

        String vmIpWorkers = configs.get(VmIpFetchTaskWorkers.value());
        int vmipwrks = NumbersUtil.parseInt(vmIpWorkers, 10);

        _vmIpFetchExecutor =   Executors.newScheduledThreadPool(vmipwrks, new NamedThreadFactory("UserVm-ipfetch"));

        String aggregationRange = configs.get("usage.stats.job.aggregation.range");
        int _usageAggregationRange  = NumbersUtil.parseInt(aggregationRange, 1440);
        int HOURLY_TIME = 60;
        final int DAILY_TIME = 60 * 24;
        if (_usageAggregationRange == DAILY_TIME) {
            _dailyOrHourly = true;
        } else if (_usageAggregationRange == HOURLY_TIME) {
            _dailyOrHourly = true;
        } else {
            _dailyOrHourly = false;
        }

        _itMgr.registerGuru(VirtualMachine.Type.User, this);

        VirtualMachine.State.getStateMachine().registerListener(new UserVmStateListener(_usageEventDao, _networkDao, _nicDao, _offeringDao, _vmDao, this, _configDao));

        String value = _configDao.getValue(Config.SetVmInternalNameUsingDisplayName.key());
        _instanceNameFlag = (value == null) ? false : Boolean.parseBoolean(value);

        _scaleRetry = NumbersUtil.parseInt(configs.get(Config.ScaleRetry.key()), 2);

        _vmIpFetchThreadExecutor = Executors.newFixedThreadPool(VmIpFetchThreadPoolMax.value(), new NamedThreadFactory("vmIpFetchThread"));

        s_logger.info("User VM Manager is configured.");

        return true;
    }

};