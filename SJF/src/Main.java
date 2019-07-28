import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int totalProcesses;

        ArrayList<Integer> arrivalTimes = new ArrayList<>();
        ArrayList<Integer> cpuTimes = new ArrayList<>();

        ArrayList<Process> allProcesses = new ArrayList<>();
        ArrayList<Process> queuedProcesses = new ArrayList<>();
        ArrayList<Process> finalListOfProcesses = new ArrayList<>();

        System.out.print("Enter total number of processes : ");
        totalProcesses = input.nextInt();

        System.out.println("Enter the arrival times");
        for (int i = 0; i < totalProcesses; i++) {
            arrivalTimes.add(input.nextInt());
        }

        System.out.println("Enter the cpu times");
        for (int i = 0; i < totalProcesses; i++) {
            cpuTimes.add(input.nextInt());
        }

        // generate all processes
        for (int i = 0; i < totalProcesses; i++) {
            allProcesses.add(
                    new Process(
                            "P" + (i + 1),
                            arrivalTimes.get(i),
                            cpuTimes.get(i)
                    )
            );
        }

        // sort all processes on the basis of arrival time
        allProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process process1, Process process2) {
                return process1.getArrivalTime() - process2.getArrivalTime();
            }
        });
        System.out.println(allProcesses);

        int timer = 0;
        int waitingTime, turnaroundTime;
        double totalWaitingTime, totalTurnaroundTime;
        double averageWaitingTime, averageTurnaroundTime;
        totalWaitingTime = totalTurnaroundTime = 0;

        /*
        calculate waiting time and turnaround time for each processes
        also, calculate total waiting time and total turnaround time
         */
        for (Process p : allProcesses) {
            waitingTime = timer - p.getArrivalTime();
            p.setWaitingTime(waitingTime);
            totalWaitingTime += waitingTime;
            timer += p.getCpuTime();
            turnaroundTime = waitingTime+p.getCpuTime();
            p.setTurnaroundTime(turnaroundTime);
            totalTurnaroundTime += turnaroundTime;
        }

        averageWaitingTime = totalWaitingTime / totalProcesses;
        averageTurnaroundTime = totalTurnaroundTime / totalProcesses;

        /*
        generate the final processes
         */
        timer = 0;
        for (int i = 0; i < totalProcesses; i++) {
            finalListOfProcesses.add(allProcesses.get(i));
            timer += allProcesses.get(i).getCpuTime();
            while (++i < totalProcesses && allProcesses.get(i).getArrivalTime() < timer) {
                queuedProcesses.add(allProcesses.get(i));
            }
            if (!queuedProcesses.isEmpty()) {
                // sort queued processes on the basis of cpu time
                queuedProcesses.sort(new Comparator<Process>() {
                    @Override
                    public int compare(Process process, Process t1) {
                        return process.getCpuTime() - t1.getCpuTime();
                    }
                });
                System.out.println(queuedProcesses);
                finalListOfProcesses.addAll(queuedProcesses);
                queuedProcesses.clear();
            }
            --i; // otherwise i will be incremented twice
        }

        /*
        print the Gantt chart
         */
        for(Process p : finalListOfProcesses) {
            System.out.print("|---" + p.getProcessId() + "---");
        }
        System.out.println("|");
        timer = 0;
        for(Process p : finalListOfProcesses) {
            System.out.printf("%-9d", timer);
            timer += p.getCpuTime();
        }
        System.out.println(timer);

        System.out.println("Average waiting time : " + averageWaitingTime);
        System.out.println("Average turnaround time : " + averageTurnaroundTime);
    }
}
