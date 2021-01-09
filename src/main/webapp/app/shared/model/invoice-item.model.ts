import { IInvoice } from 'app/shared/model/invoice.model';

export interface IInvoiceItem {
  id?: number;
  name?: string;
  amount?: number;
  invoice?: IInvoice;
}

export class InvoiceItem implements IInvoiceItem {
  constructor(public id?: number, public name?: string, public amount?: number, public invoice?: IInvoice) {}
}
