import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';

import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceItemService } from '../invoice-item/invoice-item.service';

@Component({
  selector: 'jhi-invoice-detail',
  templateUrl: './invoice-detail.component.html',
})
export class InvoiceDetailComponent implements OnInit {
  invoice: IInvoice | null = null;
  invoiceItems?: IInvoiceItem[];

  constructor(protected activatedRoute: ActivatedRoute, private invoiceItemService: InvoiceItemService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.invoice = invoice;
      this.loadItems();
    });
  }

  previousState(): void {
    window.history.back();
  }

  loadItems(): void {
    this.invoiceItemService.query({ 'invoiceId.equals': this.invoice?.id }).subscribe(response => {
      this.invoiceItems = response.body!;
    });
  }
}
