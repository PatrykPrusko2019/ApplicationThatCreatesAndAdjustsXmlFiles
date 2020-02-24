package resultAndLogFile;

import transaction.Transaction;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class LogXML {

    public LogXML() { }

    @XmlElement (name = "transaction")
    private List<Transaction> listOfLogs = new ArrayList<>();

    public List<Transaction> getListOfLogs() {
        return this.listOfLogs;
    }


}
