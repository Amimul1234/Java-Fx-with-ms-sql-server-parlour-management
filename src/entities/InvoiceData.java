package entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.StrictMath.abs;

public class InvoiceData {
    private String cutomerName;
    private String invoiceId;
    private List<AssignS> assignSList;
    private String date;

    public InvoiceData(String cutomerName) {
        this.date = new Date(System.currentTimeMillis()).toString();
        Random random = new Random();
        this.invoiceId = abs(random.nextInt()) + "";
        this.assignSList = new ArrayList<>();
        this.cutomerName = cutomerName;
    }

    public InvoiceData() {
        assignSList = null;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCutomerName() {
        return cutomerName;
    }

    public void setCutomerName(String cutomerName) {
        this.cutomerName = cutomerName;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<AssignS> getAssignSList() {
        return assignSList;
    }

    public void setAssignSList(List<AssignS> assignSList) {
        this.assignSList = assignSList;
    }

    @Override
    public String toString() {
        return "InvoiceData{" +
                "cutomerName='" + cutomerName + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", assignSList=" + assignSList +
                ", date='" + date + '\'' +
                '}';
    }
}