package mitit22kaf.serverMonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NetworkHistoryOfClassroom {
    @Id
    private short numberOfClassroom;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AverageNetworkOfClassroom> averageDownload = new ArrayList<>();

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AverageNetworkOfClassroom> averageUpload = new ArrayList<>();
}
