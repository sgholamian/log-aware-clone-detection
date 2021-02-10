//,temp,TestGreedyReservationAgent.java,67,95,temp,TestAlignedPlanner.java,697,735
//,3
public class xxx {
  @Before
  public void setup() throws Exception {

    long seed = rand.nextLong();
    rand.setSeed(seed);
    Log.info("Running with seed: " + seed);

    // setting completely loose quotas
    long timeWindow = 1000000L;
    Resource clusterCapacity = Resource.newInstance(100 * 1024, 100);
    step = 1000L;
    ReservationSystemTestUtil testUtil = new ReservationSystemTestUtil();
    String reservationQ = testUtil.getFullReservationQueueName();

    float instConstraint = 100;
    float avgConstraint = 100;

    ReservationSchedulerConfiguration conf =
        ReservationSystemTestUtil.createConf(reservationQ, timeWindow,
            instConstraint, avgConstraint);
    CapacityOverTimePolicy policy = new CapacityOverTimePolicy();
    policy.init(reservationQ, conf);
    agent = new GreedyReservationAgent();

    QueueMetrics queueMetrics = mock(QueueMetrics.class);

    plan = new InMemoryPlan(queueMetrics, policy, agent, clusterCapacity, step,
        res, minAlloc, maxAlloc, "dedicated", null, true);
  }

};