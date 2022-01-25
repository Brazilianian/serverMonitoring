package mitit22kaf.serverMonitoring.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComputerVariableData  {
    @Id
    private String ipv4;

    private int cpuLoad;
    private float networkLoad;
    private float networkUpLoad;
    private double ramLoad;

    public ComputerVariableData() {
        cpuLoad = 0;
        ipv4  = "";
        networkLoad = 0f;
        ramLoad = 0;
    }

    public ComputerVariableData(int cpuLoad, String ipv4, float networkLoad, float networkUpLoad, double ramLoad) {
        this.cpuLoad = cpuLoad;
        this.ipv4 = ipv4;
        this.networkLoad = networkLoad;
        this.networkUpLoad = networkUpLoad;
        this.ramLoad = ramLoad;
    }

    public int getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(int cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public float getNetworkLoad() {
        return networkLoad;
    }

    public void setNetworkLoad(float networkLoad) {
        this.networkLoad = networkLoad;
    }

    public float getNetworkUpLoad() {
        return networkUpLoad;
    }

    public void setNetworkUpLoad(float networkUpLoad) {
        this.networkUpLoad = networkUpLoad;
    }

    public double getRamLoad() {
        return ramLoad;
    }

    public void setRamLoad(double ramLoad) {
        this.ramLoad = ramLoad;
    }

    @Override
    public String toString() {
        return "ComputerVariableData{" +
                ", cpuLoad=" + cpuLoad +
                ", ipv4='" + ipv4 + '\'' +
                ", networkLoad='" + networkLoad + '\'' +
                ", networkUpLoad='" + networkUpLoad + '\'' +
                ", ramLoad=" + String.format("%.2f",ramLoad) +
                '}';
    }

    public void offComputer() {
        cpuLoad = 0;
        networkLoad = 0;
        networkUpLoad = 0;
        ramLoad = 0;
    }
}
