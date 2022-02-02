package mitit22kaf.serverMonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AverageNetworkOfClassroom {

    public AverageNetworkOfClassroom(Date date, float averageSpeed){
        this.date = date;
        this.averageSpeed = averageSpeed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public short id;

    private Date date;

    private float averageSpeed;
}
