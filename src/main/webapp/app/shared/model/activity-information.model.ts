import { ICategory } from 'app/shared/model/category.model';
import { ICustomer } from 'app/shared/model/customer.model';

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
  category?: ICategory;
  customer?: ICustomer;
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
    public category?: ICategory,
    public customer?: ICustomer
  ) {}
}
