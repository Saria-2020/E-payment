import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = ITransaction | ICustomer;

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html',
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;
  transactions: ITransaction[] = [];
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    invoiceNumber: [],
    date: [],
    verificationNumber: [],
    unitName: [],
    totalAmount: [],
    amountPaid: [],
    paid: [],
    transaction: [],
    customer: [],
  });

  constructor(
    protected invoiceService: InvoiceService,
    protected transactionService: TransactionService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      if (!invoice.id) {
        const today = moment().startOf('day');
        invoice.date = today;
      }

      this.updateForm(invoice);

      this.transactionService
        .query({ 'invoiceId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ITransaction[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITransaction[]) => {
          if (!invoice.transaction || !invoice.transaction.id) {
            this.transactions = resBody;
          } else {
            this.transactionService
              .find(invoice.transaction.id)
              .pipe(
                map((subRes: HttpResponse<ITransaction>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITransaction[]) => (this.transactions = concatRes));
          }
        });

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      invoiceNumber: invoice.invoiceNumber,
      date: invoice.date ? invoice.date.format(DATE_TIME_FORMAT) : null,
      verificationNumber: invoice.verificationNumber,
      unitName: invoice.unitName,
      totalAmount: invoice.totalAmount,
      amountPaid: invoice.amountPaid,
      paid: invoice.paid,
      transaction: invoice.transaction,
      customer: invoice.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      invoiceNumber: this.editForm.get(['invoiceNumber'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      verificationNumber: this.editForm.get(['verificationNumber'])!.value,
      unitName: this.editForm.get(['unitName'])!.value,
      totalAmount: this.editForm.get(['totalAmount'])!.value,
      amountPaid: this.editForm.get(['amountPaid'])!.value,
      paid: this.editForm.get(['paid'])!.value,
      transaction: this.editForm.get(['transaction'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
