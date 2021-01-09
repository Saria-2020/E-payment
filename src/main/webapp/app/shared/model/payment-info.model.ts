import { ICustomer } from 'app/shared/model/customer.model';

export interface IPaymentInfo {
  id?: number;
  name?: string;
  accountNumber?: string;
  cardNumber?: string;
  customer?: ICustomer;
}

export class PaymentInfo implements IPaymentInfo {
  constructor(
    public id?: number,
    public name?: string,
    public accountNumber?: string,
    public cardNumber?: string,
    public customer?: ICustomer
  ) {}
}
