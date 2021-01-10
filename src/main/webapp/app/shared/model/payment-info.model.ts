import { ITransaction } from 'app/shared/model/transaction.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IPaymentInfo {
  id?: number;
  name?: string;
  accountNumber?: string;
  cardNumber?: string;
  balance?: number;
  transactions?: ITransaction[];
  customer?: ICustomer;
}

export class PaymentInfo implements IPaymentInfo {
  constructor(
    public id?: number,
    public name?: string,
    public accountNumber?: string,
    public cardNumber?: string,
    public balance?: number,
    public transactions?: ITransaction[],
    public customer?: ICustomer
  ) {}
}
