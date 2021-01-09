export interface ICategory {
  id?: number;
  name?: string;
  descrpition?: any;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public descrpition?: any) {}
}
