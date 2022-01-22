package mitit22kaf.serverMonitoring.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class DiskInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float total;
    private float loaded;

    public DiskInfo() {

    }

    public DiskInfo(String name, float total, float loaded) {
        this.loaded = loaded;
        this.total = total;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getLoaded() {
        return loaded;
    }

    public void setLoaded(float loaded) {
        this.loaded = loaded;
    }

    @Override
    public String toString() {
        return "DiskInfo{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", loaded=" + loaded +
                '}';
    }
}
