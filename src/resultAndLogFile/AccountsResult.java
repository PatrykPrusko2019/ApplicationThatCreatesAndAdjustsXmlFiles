package resultAndLogFile;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class AccountsResult {

    @XmlElement (name = "accountsResult" )
    private List<AccountResultXML> resultsList = new ArrayList<>();

    public List<AccountResultXML> getListOfResult2() {
        return resultsList;
    }

    public void setListOfResult(List<AccountResultXML> listOfResult) {
        this.resultsList = listOfResult;
    }
}
