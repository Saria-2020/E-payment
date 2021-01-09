import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';

export interface ITransaction {
  id?: number;
  name?: string;
  uuid?: string;
  amount?: number;
  dateTime?: Moment;
  paymentInfo?: string;
  invoice?: IInvoice;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public name?: string,
    public uuid?: string,
    public amount?: number,
    public dateTime?: Moment,
    public paymentInfo?: string,
    public invoice?: IInvoice
  ) {}
}
