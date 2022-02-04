package mitit22kaf.serverMonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NetworkHistoryOfClassroom {

    @Id
    private Date date;

    private short numberOfClassroom;

    private float averageDownload;

    private float averageUpload;

    public NetworkHistoryOfClassroom(short numberOfClassroom, Date date) {
        this.numberOfClassroom = numberOfClassroom;
        this.date = date;
    }
}
