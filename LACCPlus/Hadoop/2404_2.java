//,temp,TestGreedyReservationAgent.java,100,134,temp,TestAlignedPlanner.java,1063,1108
//,3
public class xxx {
  @Before
  public void setup() throws Exception {

    // Initialize random seed
    long seed = rand.nextLong();
    rand.setSeed(seed);
    LOG.info("Running with seed: " + seed);

    // Set cluster parameters
    long timeWindow = 1000000L;
    int capacityMem = 100 * 1024;
    int capacityCores = 100;
    step = 60000L;

    clusterCapacity = Resource.newInstance(capacityMem, capacityCores);

    String reservationQ =
        ReservationSystemTestUtil.getFullReservationQueueName();
    float instConstraint = 100;
    float avgConstraint = 100;

    ReservationSchedulerConfiguration conf = ReservationSystemTestUtil
        .createConf(reservationQ, timeWindow, instConstraint, avgConstraint);

    CapacityOverTimePolicy policy = new CapacityOverTimePolicy();
    policy.init(reservationQ, conf);

    QueueMetrics queueMetrics = mock(QueueMetrics.class);
    RMContext context = ReservationSystemTestUtil.createMockRMContext();

    conf.setInt(AlignedPlannerWithGreedy.SMOOTHNESS_FACTOR,
        AlignedPlannerWithGreedy.DEFAULT_SMOOTHNESS_FACTOR);
    conf.setBoolean(ReservationAgent.FAVOR_EARLY_ALLOCATION, false);

    // Set planning agent
    agentRight = new AlignedPlannerWithGreedy();
    agentRight.init(conf);

    conf.setBoolean(ReservationAgent.FAVOR_EARLY_ALLOCATION, true);
    agentLeft = new AlignedPlannerWithGreedy();
    agentLeft.init(conf);

    // Create Plan
    plan = new InMemoryPlan(queueMetrics, policy, agentRight, clusterCapacity,
        step, res, minAlloc, maxAlloc, "dedicated", null, true, context);
  }

};