package com.fu.epayment.web.rest;

import com.fu.epayment.domain.Invoice;
import com.fu.epayment.domain.PaymentInfo;
import com.fu.epayment.domain.Transaction;
import com.fu.epayment.service.InvoiceService;
import com.fu.epayment.service.PaymentInfoService;
import com.fu.epayment.service.TransactionService;
import com.fu.epayment.service.dto.TransactionDTO;
import com.fu.epayment.web.rest.errors.BadRequestAlertException;
import com.fu.epayment.service.dto.TransactionCriteria;
import com.fu.epayment.service.TransactionQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.fu.epayment.domain.Transaction}.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    private static final String ENTITY_NAME = "transaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionService transactionService;

    private final TransactionQueryService transactionQueryService;

    private final InvoiceService invoiceService;

    private final PaymentInfoService paymentInfoService;

    public TransactionResource(TransactionService transactionService, TransactionQueryService transactionQueryService, InvoiceService invoiceService, PaymentInfoService paymentInfoService) {
        this.transactionService = transactionService;
        this.transactionQueryService = transactionQueryService;
        this.invoiceService = invoiceService;
        this.paymentInfoService = paymentInfoService;
    }

    /**
     * {@code POST  /transactions} : Create a new transaction.
     *
     * @param transaction the transaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transaction, or with status {@code 400 (Bad Request)} if the transaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to save Transaction : {}", transaction);
        if (transaction.getId() != null) {
            throw new BadRequestAlertException("A new transaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transaction result = transactionService.save(transaction);
        return ResponseEntity.created(new URI("/api/transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/make-payment")
    public ResponseEntity<Transaction> makePayment(@RequestBody TransactionDTO transactionDTO) throws URISyntaxException {

        if (transactionDTO.getInvoice().getTotalAmount() != null && transactionDTO.getPaymentInfo().getBalance() != null){
            double credit;;

            if (transactionDTO.getInvoice().getTotalAmount() > transactionDTO.getPaymentInfo().getBalance()){
                throw new BadRequestAlertException("You don't have enough balance to make transaction", ENTITY_NAME, "noEnough");
            }
            credit =transactionDTO.getPaymentInfo().getBalance() - transactionDTO.getInvoice().getTotalAmount();
            transactionDTO.getPaymentInfo().setBalance(credit);

        }
        paymentInfoService.save(transactionDTO.getPaymentInfo());

        transactionDTO.getInvoice().setPaid(true);
        transactionDTO.getInvoice().setAmountPaid(transactionDTO.getInvoice().getTotalAmount());
        Transaction transaction = new Transaction();
        transaction.setCustomer(transactionDTO.getInvoice().getCustomer());
        transaction.setAmount(transactionDTO.getInvoice().getTotalAmount());
        transaction.setDateTime(ZonedDateTime.now().toInstant());
        transaction.setInvoice(transactionDTO.getInvoice());
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setPaymentDetails(transactionDTO.getInvoice().getVerificationNumber());
        transaction.setPaymentInfo(transactionDTO.getPaymentInfo());


        Transaction result=transactionService.save(transaction);
        transactionDTO.getInvoice().setTransaction(result);
        System.out.println("************  "+ transactionDTO.getInvoice().getTransaction()+"  ******************");
        invoiceService.update(transactionDTO.getInvoice());


        return ResponseEntity.created(new URI("/api/make-payment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
//        return null;
    }

    /**
     * {@code PUT  /transactions} : Updates an existing transaction.
     *
     * @param transaction the transaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transaction,
     * or with status {@code 400 (Bad Request)} if the transaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transactions")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to update Transaction : {}", transaction);
        if (transaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transaction result = transactionService.save(transaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(TransactionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Transactions by criteria: {}", criteria);
        Page<Transaction> page = transactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transactions/count} : count all the transactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transactions/count")
    public ResponseEntity<Long> countTransactions(TransactionCriteria criteria) {
        log.debug("REST request to count Transactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transaction.
     *
     * @param id the id of the transaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        log.debug("REST request to get Transaction : {}", id);
        Optional<Transaction> transaction = transactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transaction);
    }

    /**
     * {@code DELETE  /transactions/:id} : delete the "id" transaction.
     *
     * @param id the id of the transaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.debug("REST request to delete Transaction : {}", id);
        transactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
