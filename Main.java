import java.util.*;

// ************************  Deadlock Detection in Scheduling *************************

// ********************* Process Class *********************************************
class Process {
    private String name;
    private int processId;

    public Process(String name, int processId) {
        this.name = name; // Process Name
        this.processId = processId; // Process ID
    }

    public String getName() {
        return name;
    } // Get Process Name method

    public int getProcessId() {
        return processId;
    } // Get Process ID method
}





// **********************************  Deadlock Class **********************************

class DeadlockDetector {
    private Map<Process, List<Process>> waitForGraph;

    public DeadlockDetector() {
        waitForGraph = new HashMap<>();
    }

    public void addProcess(Process process, List<Process> dependencies) {
        waitForGraph.put(process, new ArrayList<>(dependencies));
    }

    public boolean isDeadlocked() { // Find the deadlock Public method it return Boolean
        Set<Process> visited = new HashSet<>();
        Set<Process> inRecursionStack = new HashSet<>();

        for (Process process : waitForGraph.keySet()) {
            if (!visited.contains(process)) {
                if (hasCycle(process, visited, inRecursionStack)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasCycle(Process process, Set<Process> visited, Set<Process> inRecursionStack) {
        visited.add(process);
        inRecursionStack.add(process);

        List<Process> dependencies = waitForGraph.get(process);
        for (Process dependency : dependencies) {
            if (!visited.contains(dependency)) {
                if (hasCycle(dependency, visited, inRecursionStack)) {
                    return true;
                }
            } else if (inRecursionStack.contains(dependency)) {
                return true;
            }
        }

        inRecursionStack.remove(process);
        return false;
    }
}





// ************************** Main **********************************************


public class Main {
    public static void main(String[] args) {
        // Create Process
        Process p1 = new Process("P1", 1); // Process 1
        Process p2 = new Process("P2", 2); // Process 2
        Process p3 = new Process("P3", 3); // Process 3

        DeadlockDetector detector = new DeadlockDetector();

        // Process Hold and wait
        detector.addProcess(p1, Arrays.asList(p2)); // Process 1 -> Process 2
        detector.addProcess(p2, Arrays.asList(p3)); // Process 2 -> Process 3
        detector.addProcess(p3, Arrays.asList(p1)); // Process 3 -> Process 1

        if (detector.isDeadlocked()) {
            System.out.println("Deadlock detected!");
        } else {
            System.out.println("No deadlock detected.");
        }
    }
}

