import { IUser } from 'app/core/user/user.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IActivityInformation } from 'app/shared/model/activity-information.model';
import { IGeographicalData } from 'app/shared/model/geographical-data.model';
import { IPaymentInfo } from 'app/shared/model/payment-info.model';

export interface ICustomer {
  id?: number;
  phoneNumber?: string;
  nationalId?: string;
  user?: IUser;
  invoices?: IInvoice[];
  activityInformations?: IActivityInformation[];
  geographicalData?: IGeographicalData[];
  accounts?: IPaymentInfo[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public nationalId?: string,
    public user?: IUser,
    public invoices?: IInvoice[],
    public activityInformations?: IActivityInformation[],
    public geographicalData?: IGeographicalData[],
    public accounts?: IPaymentInfo[]
  ) {}
}
