import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentInfo, PaymentInfo } from 'app/shared/model/payment-info.model';
import { PaymentInfoService } from './payment-info.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-payment-info-update',
  templateUrl: './payment-info-update.component.html',
})
export class PaymentInfoUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    accountNumber: [],
    cardNumber: [],
    balance: [],
    customer: [],
  });

  constructor(
    protected paymentInfoService: PaymentInfoService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentInfo }) => {
      this.updateForm(paymentInfo);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(paymentInfo: IPaymentInfo): void {
    this.editForm.patchValue({
      id: paymentInfo.id,
      name: paymentInfo.name,
      accountNumber: paymentInfo.accountNumber,
      cardNumber: paymentInfo.cardNumber,
      balance: paymentInfo.balance,
      customer: paymentInfo.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentInfo = this.createFromForm();
    if (paymentInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentInfoService.update(paymentInfo));
    } else {
      this.subscribeToSaveResponse(this.paymentInfoService.create(paymentInfo));
    }
  }

  private createFromForm(): IPaymentInfo {
    return {
      ...new PaymentInfo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      cardNumber: this.editForm.get(['cardNumber'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentInfo>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
