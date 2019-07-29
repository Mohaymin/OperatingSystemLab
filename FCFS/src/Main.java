import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner get = new Scanner(System.in);

        System.out.println("Enter the number of processes -");
        int totalProcess = get.nextInt();

        System.out.println("Enter the arrival times -");
        ArrayList<Integer> arrivalTimes = new ArrayList<>();
        for (int i = 0; i < totalProcess; i++) {
            arrivalTimes.add(get.nextInt());
        }

        System.out.println("Enter the cpu times -");
        ArrayList<Integer> cpuTimes = new ArrayList<>();
        for (int i = 0; i < totalProcess; i++) {
            cpuTimes.add(get.nextInt());
        }

        // generate all processes as object
        ArrayList<Process> allProcesses = new ArrayList<>();
        for (int i = 0; i < totalProcess; i++) {
            allProcesses.add(
                    new Process(
                            "P" + (i + 1),
                            arrivalTimes.get(i),
                            cpuTimes.get(i)
                    )
            );
        }

        // sort all processes on the basis of their arrival time
        allProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process process, Process t1) {
                return process.getArriavlTime() - t1.getArriavlTime();
            }
        });

        /*
        calculate waiting time and turnaround time for each processes
        also calculate the average waiting and turnaround time
         */
        int timer;
        int waitingTime, turnaroundTime;
        double totalWaitingTime, totalTurnaroundTime;
        totalWaitingTime = totalTurnaroundTime = timer = 0;
        for (Process p : allProcesses) {
            waitingTime = timer - p.getArriavlTime();
            turnaroundTime = waitingTime + p.getCpuTime();

            p.setWaitingTime(waitingTime);
            p.setTurnaroundTime(turnaroundTime);

            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            timer += p.getCpuTime();
        }

        // print the Gantt chart
        for (Process p : allProcesses) {
            System.out.print("|---" + p.getProcessName() + "---");
        }
        System.out.println('|');
        timer = 0;
        for(Process p : allProcesses) {
            System.out.printf("%-9d", timer);
            timer += p.getCpuTime();
        }
        System.out.println(timer);
    }
}
