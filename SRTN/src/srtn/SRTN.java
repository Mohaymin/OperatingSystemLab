/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srtn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class SRTN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner get = new Scanner(System.in);

        int processCount;
        System.out.println("Enter total number of processes");
        processCount = get.nextInt();

        ArrayList<Process> allProcesses = new ArrayList<>();
        ArrayList<Integer> cpuTimes = new ArrayList<>();
        ArrayList<Integer> arrivalTimes = new ArrayList<>();
        ArrayList<String> processNames = new ArrayList<>();

        System.out.println("Enter process IDs");
        for (int i = 0; i < processCount; i++) {
            processNames.add("P" + get.next());
        }
        System.out.println("Enter arrival times");
        for (int i = 0; i < processCount; i++) {
            arrivalTimes.add(get.nextInt());
        }

        System.out.println("Enter CPU times");
        for (int i = 0; i < processCount; i++) {
            cpuTimes.add(get.nextInt());
        }

        // generate all processes
        for (int i = 0; i < processCount; i++) {
            allProcesses.add(
                    new Process(
                            processNames.get(i),
                            arrivalTimes.get(i),
                            cpuTimes.get(i)
                    )
            );
        }

        // sort all processes based on arrival time
        allProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o1.getArrivalTime() - o2.getArrivalTime(); //To change body of generated methods, choose Tools | Templates.
            }
        });

        PriorityQueue<Process> queuedProcesses = new PriorityQueue<>(
                new Comparator<Process>() {

                    @Override
                    public int compare(Process o1, Process o2) {
                        return o1.getRemainingTime() - o2.getRemainingTime(); //To change body of generated methods, choose Tools | Templates.
                    }
                }
        );

        // main calculation
        Process currentProcess, newProcess;
        currentProcess = allProcesses.get(0);
        int timer = allProcesses.get(0).getArrivalTime();

        for (int i = 1; i < processCount; i++) {
            newProcess = allProcesses.get(i);
            timer += Integer.min(newProcess.getArrivalTime(), timer + currentProcess.getRemainingTime());
            System.out.println(timer);
            currentProcess.setRemainingTime(
                    Integer.max(0, currentProcess.getRemainingTime() - newProcess.getArrivalTime())
            );
            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setFinishTime(timer);
                currentProcess.setTurnaroundTime(timer - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getArrivalTime());
                if (!queuedProcesses.isEmpty() && newProcess.getRemainingTime() < queuedProcesses.peek().getRemainingTime()) {
                    currentProcess = newProcess;
                } else {
                    currentProcess = queuedProcesses.remove();
                    queuedProcesses.add(newProcess);
                }
            } else if (currentProcess.getRemainingTime() > newProcess.getRemainingTime()) {
                queuedProcesses.add(currentProcess);
                currentProcess = newProcess;
            }
        }

        while (!queuedProcesses.isEmpty()) {
            newProcess = queuedProcesses.remove();
            timer += Integer.min(newProcess.getArrivalTime(), timer + currentProcess.getRemainingTime());
            currentProcess.setRemainingTime(
                    Integer.max(0, currentProcess.getRemainingTime() - newProcess.getArrivalTime())
            );
            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setFinishTime(timer);
                currentProcess.setTurnaroundTime(timer - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getArrivalTime());
                if (!queuedProcesses.isEmpty() && newProcess.getRemainingTime() < queuedProcesses.peek().getRemainingTime()) {
                    currentProcess = newProcess;
                } else {
                    currentProcess = queuedProcesses.remove();
                    queuedProcesses.add(newProcess);
                }
            } else if (currentProcess.getRemainingTime() > newProcess.getRemainingTime()) {
                queuedProcesses.add(currentProcess);
                currentProcess = newProcess;
            }
        }

        System.out.println("process \t arrivalTime \t cpuTime \t turnaroundTime \t waitingTime");
        for (Process p : allProcesses) {
            System.out.println(
                    p.getProcessId() + "\t\t" + p.getArrivalTime() + "\t\t"
                    + p.getCpuTime() + "\t\t" + p.getTurnaroundTime() + "\t\t"
                    + p.getWaitingTime()
            );
        }
    }
}
