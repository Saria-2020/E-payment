import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInvoiceItem, InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';

@Component({
  selector: 'jhi-invoice-item-update',
  templateUrl: './invoice-item-update.component.html',
})
export class InvoiceItemUpdateComponent implements OnInit {
  isSaving = false;
  invoices: IInvoice[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    amount: [],
    invoice: [],
  });

  constructor(
    protected invoiceItemService: InvoiceItemService,
    protected invoiceService: InvoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      this.updateForm(invoiceItem);

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(invoiceItem: IInvoiceItem): void {
    this.editForm.patchValue({
      id: invoiceItem.id,
      name: invoiceItem.name,
      amount: invoiceItem.amount,
      invoice: invoiceItem.invoice,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceItem = this.createFromForm();
    if (invoiceItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemService.update(invoiceItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemService.create(invoiceItem));
    }
  }

  private createFromForm(): IInvoiceItem {
    return {
      ...new InvoiceItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItem>>): void {
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

  trackById(index: number, item: IInvoice): any {
    return item.id;
  }
}
