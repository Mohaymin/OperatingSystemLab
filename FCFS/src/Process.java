public class Process {
    private String processName;
    private int arriavlTime;
    private int cpuTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(String processName, int arriavlTime, int cpuTime) {
        this.processName = processName;
        this.arriavlTime = arriavlTime;
        this.cpuTime = cpuTime;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getArriavlTime() {
        return arriavlTime;
    }

    public void setArriavlTime(int arriavlTime) {
        this.arriavlTime = arriavlTime;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}
