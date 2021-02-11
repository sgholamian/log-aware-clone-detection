//,temp,TestGreedyReservationAgent.java,67,95,temp,TestAlignedPlanner.java,697,735
//,3
public class xxx {
  @Before
  public void setup() throws Exception {

    // Initialize random seed
    long seed = rand.nextLong();
    rand.setSeed(seed);
    Log.info("Running with seed: " + seed);

    // Set cluster parameters
    long timeWindow = 1000000L;
    int capacityMem = 100 * 1024;
    int capacityCores = 100;
    step = 60000L;

    Resource clusterCapacity = Resource.newInstance(capacityMem, capacityCores);

    // Set configuration
    ReservationSystemTestUtil testUtil = new ReservationSystemTestUtil();
    String reservationQ = testUtil.getFullReservationQueueName();
    float instConstraint = 100;
    float avgConstraint = 100;

    ReservationSchedulerConfiguration conf =
        ReservationSystemTestUtil.createConf(reservationQ, timeWindow,
            instConstraint, avgConstraint);

    CapacityOverTimePolicy policy = new CapacityOverTimePolicy();
    policy.init(reservationQ, conf);

    QueueMetrics queueMetrics = mock(QueueMetrics.class);

    // Set planning agent
    agent = new AlignedPlannerWithGreedy();

    // Create Plan
    plan =
        new InMemoryPlan(queueMetrics, policy, agent, clusterCapacity, step,
            res, minAlloc, maxAlloc, "dedicated", null, true);
  }

};