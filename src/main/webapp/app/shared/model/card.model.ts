import { ICustomer } from 'app/shared/model/customer.model';

export interface ICard {
  id?: number;
  number?: string;
  name?: string;
  expDate?: string;
  customer?: ICustomer;
}

export class Card implements ICard {
  constructor(public id?: number, public number?: string, public name?: string, public expDate?: string, public customer?: ICustomer) {}
}
