package api.workflows;

import api.InvoiceComparator;
import api.models.InvoiceResource;
import api.redis.RedisSetup;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class InvoiceWorkflow {
    private final String SINGLE_INV_KEY = "invoiceId:";
    private Gson gson;
    private Jedis jedis;

    public InvoiceWorkflow() {
        gson = new Gson();
        jedis = RedisSetup.getConnection();
    }

    public ArrayList<InvoiceResource> getAllInvoices() {
        Set<String> keys = jedis.keys(SINGLE_INV_KEY + "*");
        ArrayList<InvoiceResource> invoiceResources = new ArrayList<InvoiceResource>();
        for (String key : keys) {
            invoiceResources.add(gson.fromJson(jedis.get(key), InvoiceResource.class));
        }

        Collections.sort(invoiceResources, new InvoiceComparator());
        return invoiceResources;
    }

    public InvoiceResource getInvoice(String id) throws Exception {
        String invoiceBlob = jedis.get(SINGLE_INV_KEY + id);
        if (invoiceBlob == null) {
            throw new Exception("Invoice does not exists: " + id);
        } else {
            return gson.fromJson(invoiceBlob, InvoiceResource.class);
        }
    }

    public InvoiceResource createInvoice(InvoiceResource invoice) throws Exception {
        if(!validateInvoice(invoice)){
            throw new Exception("Invalid resource. All fields except for description must be filled. ");
        }
        if (jedis.get(SINGLE_INV_KEY + invoice.getInvoiceNo()) != null) {
            throw new Exception("Invoice number has already been created. ");
        }

        String blob = gson.toJson(invoice);
        String invoiceKey = SINGLE_INV_KEY + invoice.getInvoiceNo();
        jedis.set(invoiceKey, blob);
        return gson.fromJson(jedis.get(invoiceKey), InvoiceResource.class);
    }

    public InvoiceResource updateInvoice(String id, InvoiceResource updatedInvoice) throws Exception {
        if(!validateInvoice(updatedInvoice)){
            throw new Exception("Invalid resource. All fields except for description must be filled. ");
        }
        String invoiceKey = SINGLE_INV_KEY + id;
        String blob = jedis.get(invoiceKey);
        InvoiceResource invoiceResource = gson.fromJson(blob, InvoiceResource.class);
        if(!(invoiceResource.getInvoiceNo().equals(updatedInvoice.getInvoiceNo()))){
            throw new Exception("Invoice numbers cannot be changed");
        }

        blob = gson.toJson(updatedInvoice);
        jedis.set(invoiceKey, blob);

        return gson.fromJson(jedis.get(invoiceKey), InvoiceResource.class);
    }

    public String deleteInvoice(String id) throws Exception {
        jedis.del(SINGLE_INV_KEY + id);

        if (jedis.get(SINGLE_INV_KEY + id) != null) {
            throw new Exception("Delete failed.  Still found " + id);
        }
        return jedis.get(SINGLE_INV_KEY + id);
    }

    private boolean validateInvoice(InvoiceResource invoice){
        if(invoice.getInvoiceNo() == null ||
                invoice.getCompanyName() == null ||
                invoice.getPrice() == null ||
                invoice.getStatus() == null ||
                invoice.getTypeOfWork() == null ||
                invoice.getDueDate() == null){
            return false;
        }else{
            return true;
        }
    }
}
