package blake.rps;
/*******************************************************************
 *  RpsHibernateJsonEntry class
 *  Description: This is where I build the relationship for the
 *  SQL table json_entry from the history database along with
 *  the getters and setters for db interaction.
 *******************************************************************/

// Imported Libraries
import javax.persistence.*;

@Entity
@Table(name = "json_entry")
public class RpsHibernateJsonEntry {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "attr")
    private String attr;

    public String getJson() {
        return attr;
    }
    public void setJson(String attr) {
        this.attr = attr;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

}
