import { IUser } from 'app/core/user/user.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IActivityInformation } from 'app/shared/model/activity-information.model';
import { IGeographicalData } from 'app/shared/model/geographical-data.model';
import { ICard } from 'app/shared/model/card.model';

export interface ICustomer {
  id?: number;
  phoneNumber?: string;
  user?: IUser;
  invoices?: IInvoice[];
  activityInformations?: IActivityInformation[];
  geographicalData?: IGeographicalData[];
  cards?: ICard[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public user?: IUser,
    public invoices?: IInvoice[],
    public activityInformations?: IActivityInformation[],
    public geographicalData?: IGeographicalData[],
    public cards?: ICard[]
  ) {}
}
