import { IActivityInformation } from 'app/shared/model/activity-information.model';

export interface ICategory {
  id?: number;
  name?: string;
  description?: any;
  activityInformations?: IActivityInformation[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public description?: any, public activityInformations?: IActivityInformation[]) {}
}
