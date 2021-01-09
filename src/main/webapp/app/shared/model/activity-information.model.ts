import { ICustomer } from 'app/shared/model/customer.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IActivityInformation {
  id?: number;
  name?: string;
  sector?: string;
  typeOfActivity?: string;
  propertyType?: string;
  areaClass?: string;
  architectureType?: string;
  numberOfFloors?: string;
  features?: string;
  descriptionOfTheFeatures?: any;
  customer?: ICustomer;
  category?: ICategory;
}

export class ActivityInformation implements IActivityInformation {
  constructor(
    public id?: number,
    public name?: string,
    public sector?: string,
    public typeOfActivity?: string,
    public propertyType?: string,
    public areaClass?: string,
    public architectureType?: string,
    public numberOfFloors?: string,
    public features?: string,
    public descriptionOfTheFeatures?: any,
    public customer?: ICustomer,
    public category?: ICategory
  ) {}
}
