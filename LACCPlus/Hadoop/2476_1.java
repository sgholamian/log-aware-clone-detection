//,temp,TestQueueParsing.java,369,436,temp,TestQueueParsing.java,310,367
//,3
public class xxx {
  private void setupQueueConfigurationWithLabelsAndReleaseCheck
      (CapacitySchedulerConfiguration conf) {
    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] {"a", "b"});
    conf.setCapacityByLabel(CapacitySchedulerConfiguration.ROOT, "red", 100);
    conf.setCapacityByLabel(CapacitySchedulerConfiguration.ROOT, "blue", 100);

    final String A = CapacitySchedulerConfiguration.ROOT + ".a";
    // The cap <= max-cap check is not needed
    conf.setCapacity(A, 50);
    conf.setMaximumCapacity(A, 100);

    final String B = CapacitySchedulerConfiguration.ROOT + ".b";
    conf.setCapacity(B, 50);
    conf.setMaximumCapacity(B, 100);

    LOG.info("Setup top-level queues");

    // Define 2nd-level queues
    final String A1 = A + ".a1";
    final String A2 = A + ".a2";
    conf.setQueues(A, new String[] {"a1", "a2"});
    conf.setAccessibleNodeLabels(A, ImmutableSet.of("red", "blue"));
    conf.setCapacityByLabel(A, "red", 50);
    conf.setMaximumCapacityByLabel(A, "red", 100);
    conf.setCapacityByLabel(A, "blue", 30);
    conf.setMaximumCapacityByLabel(A, "blue", 50);

    conf.setCapacity(A1, 60);
    conf.setMaximumCapacity(A1, 60);
    conf.setCapacityByLabel(A1, "red", 60);
    conf.setMaximumCapacityByLabel(A1, "red", 30);
    conf.setCapacityByLabel(A1, "blue", 100);
    conf.setMaximumCapacityByLabel(A1, "blue", 100);

    conf.setCapacity(A2, 40);
    conf.setMaximumCapacity(A2, 85);
    conf.setAccessibleNodeLabels(A2, ImmutableSet.of("red"));
    conf.setCapacityByLabel(A2, "red", 40);
    conf.setMaximumCapacityByLabel(A2, "red", 60);

    final String B1 = B + ".b1";
    final String B2 = B + ".b2";
    final String B3 = B + ".b3";
    conf.setQueues(B, new String[] {"b1", "b2", "b3"});
    conf.setAccessibleNodeLabels(B, ImmutableSet.of("red", "blue"));
    conf.setCapacityByLabel(B, "red", 50);
    conf.setMaximumCapacityByLabel(B, "red", 100);
    conf.setCapacityByLabel(B, "blue", 70);
    conf.setMaximumCapacityByLabel(B, "blue", 100);

    conf.setCapacity(B1, 10);
    conf.setMaximumCapacity(B1, 10);
    conf.setCapacityByLabel(B1, "red", 60);
    conf.setMaximumCapacityByLabel(B1, "red", 30);
    conf.setCapacityByLabel(B1, "blue", 50);
    conf.setMaximumCapacityByLabel(B1, "blue", 100);

    conf.setCapacity(B2, 80);
    conf.setMaximumCapacity(B2, 40);
    conf.setCapacityByLabel(B2, "red", 30);
    conf.setCapacityByLabel(B2, "blue", 25);

    conf.setCapacity(B3, 10);
    conf.setMaximumCapacity(B3, 25);
    conf.setCapacityByLabel(B3, "red", 10);
    conf.setCapacityByLabel(B3, "blue", 25);
  }

};