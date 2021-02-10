//,temp,TestGreedyReservationAgent.java,100,134,temp,TestAlignedPlanner.java,1063,1108
//,3
public class xxx {
  @Before
  public void setup() throws Exception {

    long seed = rand.nextLong();
    rand.setSeed(seed);
    LOG.info("Running with seed: " + seed);

    // setting completely loose quotas
    long timeWindow = 1000000L;
    Resource clusterCapacity = Resource.newInstance(100 * 1024, 100);
    step = 1000L;
    String reservationQ =
        ReservationSystemTestUtil.getFullReservationQueueName();

    float instConstraint = 100;
    float avgConstraint = 100;

    ReservationSchedulerConfiguration conf =
        ReservationSystemTestUtil.createConf(reservationQ, timeWindow,
            instConstraint, avgConstraint);
    CapacityOverTimePolicy policy = new CapacityOverTimePolicy();
    policy.init(reservationQ, conf);

    // setting conf to
    conf.setBoolean(GreedyReservationAgent.FAVOR_EARLY_ALLOCATION,
        allocateLeft);
    agent = new GreedyReservationAgent();
    agent.init(conf);

    QueueMetrics queueMetrics = mock(QueueMetrics.class);
    RMContext context = ReservationSystemTestUtil.createMockRMContext();

    plan = new InMemoryPlan(queueMetrics, policy, agent, clusterCapacity, step,
        res, minAlloc, maxAlloc, "dedicated", null, true, context);
  }

};