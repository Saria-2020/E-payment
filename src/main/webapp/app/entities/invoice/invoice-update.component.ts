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
import { ICard } from 'app/shared/model/card.model';
import { CardService } from 'app/entities/card/card.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = ICard | ICustomer;

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html',
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;
  cards: ICard[] = [];
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    uniqueNumberCustomer: [],
    nameOfTheCardOwner: [],
    cardExpirationDate: [],
    verificationNumber: [],
    transactionNumber: [],
    invoiceNumber: [],
    unitName: [],
    customerName: [],
    amountOfTheInvoice: [],
    amountPaid: [],
    card: [],
    customer: [],
  });

  constructor(
    protected invoiceService: InvoiceService,
    protected cardService: CardService,
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

      this.cardService
        .query({ 'invoiceId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ICard[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICard[]) => {
          if (!invoice.card || !invoice.card.id) {
            this.cards = resBody;
          } else {
            this.cardService
              .find(invoice.card.id)
              .pipe(
                map((subRes: HttpResponse<ICard>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICard[]) => (this.cards = concatRes));
          }
        });

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      date: invoice.date ? invoice.date.format(DATE_TIME_FORMAT) : null,
      uniqueNumberCustomer: invoice.uniqueNumberCustomer,
      nameOfTheCardOwner: invoice.nameOfTheCardOwner,
      cardExpirationDate: invoice.cardExpirationDate,
      verificationNumber: invoice.verificationNumber,
      transactionNumber: invoice.transactionNumber,
      invoiceNumber: invoice.invoiceNumber,
      unitName: invoice.unitName,
      customerName: invoice.customerName,
      amountOfTheInvoice: invoice.amountOfTheInvoice,
      amountPaid: invoice.amountPaid,
      card: invoice.card,
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
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      uniqueNumberCustomer: this.editForm.get(['uniqueNumberCustomer'])!.value,
      nameOfTheCardOwner: this.editForm.get(['nameOfTheCardOwner'])!.value,
      cardExpirationDate: this.editForm.get(['cardExpirationDate'])!.value,
      verificationNumber: this.editForm.get(['verificationNumber'])!.value,
      transactionNumber: this.editForm.get(['transactionNumber'])!.value,
      invoiceNumber: this.editForm.get(['invoiceNumber'])!.value,
      unitName: this.editForm.get(['unitName'])!.value,
      customerName: this.editForm.get(['customerName'])!.value,
      amountOfTheInvoice: this.editForm.get(['amountOfTheInvoice'])!.value,
      amountPaid: this.editForm.get(['amountPaid'])!.value,
      card: this.editForm.get(['card'])!.value,
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
