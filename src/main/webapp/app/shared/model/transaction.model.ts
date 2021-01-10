import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IPaymentInfo } from 'app/shared/model/payment-info.model';

export interface ITransaction {
  id?: number;
  name?: string;
  uuid?: string;
  amount?: number;
  dateTime?: Moment;
  paymentDetails?: string;
  invoice?: IInvoice;
  customer?: ICustomer;
  paymentInfo?: IPaymentInfo;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public name?: string,
    public uuid?: string,
    public amount?: number,
    public dateTime?: Moment,
    public paymentDetails?: string,
    public invoice?: IInvoice,
    public customer?: ICustomer,
    public paymentInfo?: IPaymentInfo
  ) {}
}
