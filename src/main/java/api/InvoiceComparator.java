package api;

import api.models.InvoiceResource;

import java.util.Comparator;

public class InvoiceComparator implements Comparator<InvoiceResource> {
    @Override
    public int compare(InvoiceResource o1, InvoiceResource o2) {
        return o1.getInvoiceNo().compareTo(o2.getInvoiceNo());
    }
}
