import java.util.Date;
import java.util.ArrayList;
import java.util.List;

class DateRange {
  private Date start, end;

  public DateRange(Date start, Date end) {
    this.start = start;
    this.end = end;
  }

  public Date getStart() { return this.start; }
  public Date getEnd() { return this.end; }

  public boolean overlaps(DateRange otherDateRange) {
    return !(this.end.before(otherDateRange.getStart()) || this.start.after(otherDateRange.getEnd()));
  }
}

class WestVillageManagementVariables{

    private String tenant;
    private DateRange leaseRange;
    private double amountInvoiced;
    private double amountReceived;

    WestVillageManagementVariables(String tenant, DateRange leaseRange, double amountInvoiced, double amountReceived){

        this.tenant = tenant;
        this.leaseRange = leaseRange;
        this.amountInvoiced = amountInvoiced;
        this.amountReceived = amountReceived;
    }

    public String getTenant(){return this.tenant;}
    public DateRange getLeaseRange(){return this.leaseRange;}
    public double getAmountInvoiced(){return this.amountInvoiced;}
    public double getAmountReceived(){return this.amountReceived;}


}



class WestVillageManagementVariation {

  private List<WestVillageManagementVariables> records = new ArrayList<>();

  public WestVillageManagementVariation() {};

  public void addLeaseRecord(WestVillageManagementVariables westVillageVars) {
    records.add(new WestVillageManagementVariables(
      westVillageVars.getTenant(),
      westVillageVars.getLeaseRange(),
      westVillageVars.getAmountInvoiced(),
      westVillageVars.getAmountReceived()
    ));
  }

  public double amountInvoiceIn(DateRange dateRange) {
    double total = 0;
    for (WestVillageManagementVariables r : records) {
      if (r.getLeaseRange().overlaps(dateRange)) {
        total += r.getAmountInvoiced();
      }
    }
    return total;
  }

  public double amountReceivedIn(DateRange dateRange) {
    double total = 0;
    for (WestVillageManagementVariables r : records) {
      if (r.getLeaseRange().overlaps(dateRange)) {
        total += r.getAmountReceived();
      }
    }
    return total;
  }

  public double amountOverdue(DateRange dateRange) {
    double total = 0;
    for (WestVillageManagementVariables r : records) {
      if (r.getLeaseRange().overlaps(dateRange)) {
        total += (r.getAmountInvoiced() - r.getAmountReceived());
      }
    }
    return total;
  }
}

public class DCGE2 {
  public static void main(String[] args) {
    WestVillageManagementVariation mgmt = new WestVillageManagementVariation();

    DateRange range1 = new DateRange(
      new Date(2025 - 1900, 5, 1),
      new Date(2025 - 1900, 7, 31)
    );
    mgmt.addLeaseRecord(new WestVillageManagementVariables("Alice", range1, 23400.0, 23400.0));

    DateRange range2 = new DateRange(
      new Date(2025 - 1900, 8, 1),
      new Date(2025 - 1900, 10, 30)
    );
    mgmt.addLeaseRecord(new WestVillageManagementVariables("Bob", range2, 13800.0, 9200.0));

    DateRange queryRange = new DateRange(
      new Date(2025 - 1900, 6, 1),
      new Date(2025 - 1900, 9, 31)
    );

    System.out.println("Total Invoiced: $" + mgmt.amountInvoiceIn(queryRange));
    System.out.println("Total Received: $" + mgmt.amountReceivedIn(queryRange));
    System.out.println("Total Overdue: $" + mgmt.amountOverdue(queryRange));
  }
}