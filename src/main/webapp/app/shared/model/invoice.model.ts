import { Moment } from 'moment';
import { ITransaction } from 'app/shared/model/transaction.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IInvoice {
  id?: number;
  invoiceNumber?: string;
  date?: Moment;
  verificationNumber?: string;
  unitName?: string;
  amountOfTheInvoice?: string;
  amountPaid?: string;
  transaction?: ITransaction;
  customer?: ICustomer;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public invoiceNumber?: string,
    public date?: Moment,
    public verificationNumber?: string,
    public unitName?: string,
    public amountOfTheInvoice?: string,
    public amountPaid?: string,
    public transaction?: ITransaction,
    public customer?: ICustomer
  ) {}
}
