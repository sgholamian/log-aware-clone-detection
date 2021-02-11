//,temp,AllocationFileLoaderService.java,416,541,temp,AllocationFileLoaderService.java,204,411
//,3
public class xxx {
  public synchronized void reloadAllocations() throws IOException,
      ParserConfigurationException, SAXException, AllocationConfigurationException {
    if (allocFile == null) {
      return;
    }
    LOG.info("Loading allocation file " + allocFile);
    // Create some temporary hashmaps to hold the new allocs, and we only save
    // them in our fields if we have parsed the entire allocs file successfully.
    Map<String, Resource> minQueueResources = new HashMap<String, Resource>();
    Map<String, Resource> maxQueueResources = new HashMap<String, Resource>();
    Map<String, Integer> queueMaxApps = new HashMap<String, Integer>();
    Map<String, Integer> userMaxApps = new HashMap<String, Integer>();
    Map<String, Float> queueMaxAMShares = new HashMap<String, Float>();
    Map<String, ResourceWeights> queueWeights = new HashMap<String, ResourceWeights>();
    Map<String, SchedulingPolicy> queuePolicies = new HashMap<String, SchedulingPolicy>();
    Map<String, Long> minSharePreemptionTimeouts = new HashMap<String, Long>();
    Map<String, Long> fairSharePreemptionTimeouts = new HashMap<String, Long>();
    Map<String, Float> fairSharePreemptionThresholds =
        new HashMap<String, Float>();
    Map<String, Map<QueueACL, AccessControlList>> queueAcls =
        new HashMap<String, Map<QueueACL, AccessControlList>>();
    Set<String> reservableQueues = new HashSet<String>();
    int userMaxAppsDefault = Integer.MAX_VALUE;
    int queueMaxAppsDefault = Integer.MAX_VALUE;
    float queueMaxAMShareDefault = 0.5f;
    long defaultFairSharePreemptionTimeout = Long.MAX_VALUE;
    long defaultMinSharePreemptionTimeout = Long.MAX_VALUE;
    float defaultFairSharePreemptionThreshold = 0.5f;
    SchedulingPolicy defaultSchedPolicy = SchedulingPolicy.DEFAULT_POLICY;

    // Reservation global configuration knobs
    String planner = null;
    String reservationAgent = null;
    String reservationAdmissionPolicy = null;

    QueuePlacementPolicy newPlacementPolicy = null;

    // Remember all queue names so we can display them on web UI, etc.
    // configuredQueues is segregated based on whether it is a leaf queue
    // or a parent queue. This information is used for creating queues
    // and also for making queue placement decisions(QueuePlacementRule.java).
    Map<FSQueueType, Set<String>> configuredQueues =
        new HashMap<FSQueueType, Set<String>>();
    for (FSQueueType queueType : FSQueueType.values()) {
      configuredQueues.put(queueType, new HashSet<String>());
    }

    // Read and parse the allocations file.
    DocumentBuilderFactory docBuilderFactory =
      DocumentBuilderFactory.newInstance();
    docBuilderFactory.setIgnoringComments(true);
    DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
    Document doc = builder.parse(allocFile);
    Element root = doc.getDocumentElement();
    if (!"allocations".equals(root.getTagName()))
      throw new AllocationConfigurationException("Bad fair scheduler config " +
          "file: top-level element not <allocations>");
    NodeList elements = root.getChildNodes();
    List<Element> queueElements = new ArrayList<Element>();
    Element placementPolicyElement = null;
    for (int i = 0; i < elements.getLength(); i++) {
      Node node = elements.item(i);
      if (node instanceof Element) {
        Element element = (Element)node;
        if ("queue".equals(element.getTagName()) ||
          "pool".equals(element.getTagName())) {
          queueElements.add(element);
        } else if ("user".equals(element.getTagName())) {
          String userName = element.getAttribute("name");
          NodeList fields = element.getChildNodes();
          for (int j = 0; j < fields.getLength(); j++) {
            Node fieldNode = fields.item(j);
            if (!(fieldNode instanceof Element))
              continue;
            Element field = (Element) fieldNode;
            if ("maxRunningApps".equals(field.getTagName())) {
              String text = ((Text)field.getFirstChild()).getData().trim();
              int val = Integer.parseInt(text);
              userMaxApps.put(userName, val);
            }
          }
        } else if ("userMaxAppsDefault".equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          int val = Integer.parseInt(text);
          userMaxAppsDefault = val;
        } else if ("defaultFairSharePreemptionTimeout"
            .equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          long val = Long.parseLong(text) * 1000L;
          defaultFairSharePreemptionTimeout = val;
        } else if ("fairSharePreemptionTimeout".equals(element.getTagName())) {
          if (defaultFairSharePreemptionTimeout == Long.MAX_VALUE) {
            String text = ((Text)element.getFirstChild()).getData().trim();
            long val = Long.parseLong(text) * 1000L;
            defaultFairSharePreemptionTimeout = val;
          }
        } else if ("defaultMinSharePreemptionTimeout"
            .equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          long val = Long.parseLong(text) * 1000L;
          defaultMinSharePreemptionTimeout = val;
        } else if ("defaultFairSharePreemptionThreshold"
            .equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          float val = Float.parseFloat(text);
          val = Math.max(Math.min(val, 1.0f), 0.0f);
          defaultFairSharePreemptionThreshold = val;
        } else if ("queueMaxAppsDefault".equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          int val = Integer.parseInt(text);
          queueMaxAppsDefault = val;
        } else if ("queueMaxAMShareDefault".equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          float val = Float.parseFloat(text);
          val = Math.min(val, 1.0f);
          queueMaxAMShareDefault = val;
        } else if ("defaultQueueSchedulingPolicy".equals(element.getTagName())
            || "defaultQueueSchedulingMode".equals(element.getTagName())) {
          String text = ((Text)element.getFirstChild()).getData().trim();
          defaultSchedPolicy = SchedulingPolicy.parse(text);
        } else if ("queuePlacementPolicy".equals(element.getTagName())) {
          placementPolicyElement = element;
        } else if ("reservation-planner".equals(element.getTagName())) {
          String text = ((Text) element.getFirstChild()).getData().trim();
          planner = text;
        } else if ("reservation-agent".equals(element.getTagName())) {
          String text = ((Text) element.getFirstChild()).getData().trim();
          reservationAgent = text;
        } else if ("reservation-policy".equals(element.getTagName())) {
          String text = ((Text) element.getFirstChild()).getData().trim();
          reservationAdmissionPolicy = text;
        } else {
          LOG.warn("Bad element in allocations file: " + element.getTagName());
        }
      }
    }

    // Load queue elements.  A root queue can either be included or omitted.  If
    // it's included, all other queues must be inside it.
    for (Element element : queueElements) {
      String parent = "root";
      if (element.getAttribute("name").equalsIgnoreCase("root")) {
        if (queueElements.size() > 1) {
          throw new AllocationConfigurationException("If configuring root queue,"
              + " no other queues can be placed alongside it.");
        }
        parent = null;
      }
      loadQueue(parent, element, minQueueResources, maxQueueResources,
          queueMaxApps, userMaxApps, queueMaxAMShares, queueWeights,
          queuePolicies, minSharePreemptionTimeouts, fairSharePreemptionTimeouts,
          fairSharePreemptionThresholds, queueAcls, configuredQueues,
          reservableQueues);
    }

    // Load placement policy and pass it configured queues
    Configuration conf = getConfig();
    if (placementPolicyElement != null) {
      newPlacementPolicy = QueuePlacementPolicy.fromXml(placementPolicyElement,
          configuredQueues, conf);
    } else {
      newPlacementPolicy = QueuePlacementPolicy.fromConfiguration(conf,
          configuredQueues);
    }

    // Set the min/fair share preemption timeout for the root queue
    if (!minSharePreemptionTimeouts.containsKey(QueueManager.ROOT_QUEUE)){
      minSharePreemptionTimeouts.put(QueueManager.ROOT_QUEUE,
          defaultMinSharePreemptionTimeout);
    }
    if (!fairSharePreemptionTimeouts.containsKey(QueueManager.ROOT_QUEUE)) {
      fairSharePreemptionTimeouts.put(QueueManager.ROOT_QUEUE,
          defaultFairSharePreemptionTimeout);
    }

    // Set the fair share preemption threshold for the root queue
    if (!fairSharePreemptionThresholds.containsKey(QueueManager.ROOT_QUEUE)) {
      fairSharePreemptionThresholds.put(QueueManager.ROOT_QUEUE,
          defaultFairSharePreemptionThreshold);
    }

    ReservationQueueConfiguration globalReservationQueueConfig = new
        ReservationQueueConfiguration();
    if (planner != null) {
      globalReservationQueueConfig.setPlanner(planner);
    }
    if (reservationAdmissionPolicy != null) {
      globalReservationQueueConfig.setReservationAdmissionPolicy
          (reservationAdmissionPolicy);
    }
    if (reservationAgent != null) {
      globalReservationQueueConfig.setReservationAgent(reservationAgent);
    }

    AllocationConfiguration info = new AllocationConfiguration(minQueueResources,
        maxQueueResources, queueMaxApps, userMaxApps, queueWeights,
        queueMaxAMShares, userMaxAppsDefault, queueMaxAppsDefault,
        queueMaxAMShareDefault, queuePolicies, defaultSchedPolicy,
        minSharePreemptionTimeouts, fairSharePreemptionTimeouts,
        fairSharePreemptionThresholds, queueAcls,
        newPlacementPolicy, configuredQueues, globalReservationQueueConfig,
        reservableQueues);
    
    lastSuccessfulReload = clock.getTime();
    lastReloadAttemptFailed = false;

    reloadListener.onReload(info);
  }

};