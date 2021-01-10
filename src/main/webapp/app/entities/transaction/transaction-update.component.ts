import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IPaymentInfo } from 'app/shared/model/payment-info.model';
import { PaymentInfoService } from 'app/entities/payment-info/payment-info.service';

type SelectableEntity = ICustomer | IPaymentInfo;

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  paymentinfos: IPaymentInfo[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    uuid: [],
    amount: [],
    dateTime: [],
    paymentDetails: [],
    customer: [],
    paymentInfo: [],
  });

  constructor(
    protected transactionService: TransactionService,
    protected customerService: CustomerService,
    protected paymentInfoService: PaymentInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      if (!transaction.id) {
        const today = moment().startOf('day');
        transaction.dateTime = today;
      }

      this.updateForm(transaction);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));

      this.paymentInfoService.query().subscribe((res: HttpResponse<IPaymentInfo[]>) => (this.paymentinfos = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      name: transaction.name,
      uuid: transaction.uuid,
      amount: transaction.amount,
      dateTime: transaction.dateTime ? transaction.dateTime.format(DATE_TIME_FORMAT) : null,
      paymentDetails: transaction.paymentDetails,
      customer: transaction.customer,
      paymentInfo: transaction.paymentInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      dateTime: this.editForm.get(['dateTime'])!.value ? moment(this.editForm.get(['dateTime'])!.value, DATE_TIME_FORMAT) : undefined,
      paymentDetails: this.editForm.get(['paymentDetails'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      paymentInfo: this.editForm.get(['paymentInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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
