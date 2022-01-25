package mitit22kaf.serverMonitoring.sort;

import mitit22kaf.serverMonitoring.entities.ComputerData;

import java.util.Comparator;

public class SortComputerDataByNumber implements Comparator<ComputerData> {
    @Override
    public int compare(ComputerData o1, ComputerData o2) {
        return o1.getNumberPс() - o2.getNumberPс();
    }
}
