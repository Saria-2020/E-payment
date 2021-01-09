import { Moment } from 'moment';
import { ITransaction } from 'app/shared/model/transaction.model';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IInvoice {
  id?: number;
  invoiceNumber?: string;
  date?: Moment;
  verificationNumber?: string;
  unitName?: string;
  totalAmount?: number;
  amountPaid?: number;
  paid?: boolean;
  transaction?: ITransaction;
  items?: IInvoiceItem[];
  customer?: ICustomer;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public invoiceNumber?: string,
    public date?: Moment,
    public verificationNumber?: string,
    public unitName?: string,
    public totalAmount?: number,
    public amountPaid?: number,
    public paid?: boolean,
    public transaction?: ITransaction,
    public items?: IInvoiceItem[],
    public customer?: ICustomer
  ) {
    this.paid = this.paid || false;
  }
}
