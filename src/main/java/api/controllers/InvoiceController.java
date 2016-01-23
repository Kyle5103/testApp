package api.controllers;

import api.models.InvoiceResource;
import api.models.UserResource;
import api.redis.RedisSetup;
import api.workflows.InvoiceWorkflow;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class InvoiceController {
    private final String SINGLE_INV_KEY = "invoiceId:";

    @RequestMapping(value = "/invoices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInvoices() {
        return new ResponseEntity(new InvoiceWorkflow().getAllInvoices(), HttpStatus.OK);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInvoice(@PathVariable String id) {
        if(RedisSetup.getConnection().get(SINGLE_INV_KEY + id) == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(new InvoiceWorkflow().getInvoice(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteInvoice(@PathVariable String id) {
        if(RedisSetup.getConnection().get(SINGLE_INV_KEY + id) == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(new InvoiceWorkflow().deleteInvoice(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createInvoice(@RequestBody InvoiceResource invoice) {
        try {
            return new ResponseEntity(new InvoiceWorkflow().createInvoice(invoice), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateInvoice(@PathVariable String id, @RequestBody InvoiceResource invoice) {
        if(RedisSetup.getConnection().get(SINGLE_INV_KEY + id) == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(new InvoiceWorkflow().updateInvoice(id, invoice), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
