//,temp,TestQueueParsing.java,369,436,temp,TestQueueParsing.java,310,367
//,3
public class xxx {
  private void setupQueueConfigurationWithLabels(CapacitySchedulerConfiguration conf) {
    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] {"a", "b"});
    conf.setCapacityByLabel(CapacitySchedulerConfiguration.ROOT, "red", 100);
    conf.setCapacityByLabel(CapacitySchedulerConfiguration.ROOT, "blue", 100);

    final String A = CapacitySchedulerConfiguration.ROOT + ".a";
    conf.setCapacity(A, 10);
    conf.setMaximumCapacity(A, 15);
    
    final String B = CapacitySchedulerConfiguration.ROOT + ".b";
    conf.setCapacity(B, 90);

    LOG.info("Setup top-level queues");
    
    // Define 2nd-level queues
    final String A1 = A + ".a1";
    final String A2 = A + ".a2";
    conf.setQueues(A, new String[] {"a1", "a2"});
    conf.setAccessibleNodeLabels(A, ImmutableSet.of("red", "blue"));
    conf.setCapacityByLabel(A, "red", 50);
    conf.setMaximumCapacityByLabel(A, "red", 50);
    conf.setCapacityByLabel(A, "blue", 50);
    
    conf.setCapacity(A1, 30);
    conf.setMaximumCapacity(A1, 45);
    conf.setCapacityByLabel(A1, "red", 50);
    conf.setCapacityByLabel(A1, "blue", 100);
    
    conf.setCapacity(A2, 70);
    conf.setMaximumCapacity(A2, 85);
    conf.setAccessibleNodeLabels(A2, ImmutableSet.of("red"));
    conf.setCapacityByLabel(A2, "red", 50);
    conf.setMaximumCapacityByLabel(A2, "red", 60);
    
    final String B1 = B + ".b1";
    final String B2 = B + ".b2";
    final String B3 = B + ".b3";
    conf.setQueues(B, new String[] {"b1", "b2", "b3"});
    conf.setAccessibleNodeLabels(B, ImmutableSet.of("red", "blue"));
    conf.setCapacityByLabel(B, "red", 50);
    conf.setCapacityByLabel(B, "blue", 50);
    
    conf.setCapacity(B1, 50);
    conf.setMaximumCapacity(B1, 85);
    conf.setCapacityByLabel(B1, "red", 50);
    conf.setCapacityByLabel(B1, "blue", 50);
    
    conf.setCapacity(B2, 30);
    conf.setMaximumCapacity(B2, 35);
    conf.setCapacityByLabel(B2, "red", 25);
    conf.setCapacityByLabel(B2, "blue", 25);
    
    conf.setCapacity(B3, 20);
    conf.setMaximumCapacity(B3, 35);
    conf.setCapacityByLabel(B3, "red", 25);
    conf.setCapacityByLabel(B3, "blue", 25);
  }

};