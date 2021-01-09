import { ICustomer } from 'app/shared/model/customer.model';

export interface IGeographicalData {
  id?: number;
  state?: string;
  units?: string;
  district?: string;
  square?: string;
  realEstateNumber?: string;
  activityNumber?: string;
  areaOfTheRealEstate?: string;
  customer?: ICustomer;
}

export class GeographicalData implements IGeographicalData {
  constructor(
    public id?: number,
    public state?: string,
    public units?: string,
    public district?: string,
    public square?: string,
    public realEstateNumber?: string,
    public activityNumber?: string,
    public areaOfTheRealEstate?: string,
    public customer?: ICustomer
  ) {}
}
