import { Moment } from 'moment';
import { ICard } from 'app/shared/model/card.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IInvoice {
  id?: number;
  date?: Moment;
  uniqueNumberCustomer?: string;
  nameOfTheCardOwner?: string;
  cardExpirationDate?: string;
  verificationNumber?: string;
  transactionNumber?: string;
  invoiceNumber?: string;
  unitName?: string;
  customerName?: string;
  amountOfTheInvoice?: string;
  amountPaid?: string;
  card?: ICard;
  customer?: ICustomer;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public date?: Moment,
    public uniqueNumberCustomer?: string,
    public nameOfTheCardOwner?: string,
    public cardExpirationDate?: string,
    public verificationNumber?: string,
    public transactionNumber?: string,
    public invoiceNumber?: string,
    public unitName?: string,
    public customerName?: string,
    public amountOfTheInvoice?: string,
    public amountPaid?: string,
    public card?: ICard,
    public customer?: ICustomer
  ) {}
}
