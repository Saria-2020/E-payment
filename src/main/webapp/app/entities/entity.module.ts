import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.EPaymentCustomerModule),
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.EPaymentInvoiceModule),
      },
      {
        path: 'geographical-data',
        loadChildren: () => import('./geographical-data/geographical-data.module').then(m => m.EPaymentGeographicalDataModule),
      },
      {
        path: 'activity-information',
        loadChildren: () => import('./activity-information/activity-information.module').then(m => m.EPaymentActivityInformationModule),
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.EPaymentCategoryModule),
      },
      {
        path: 'card',
        loadChildren: () => import('./card/card.module').then(m => m.EPaymentCardModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EPaymentEntityModule {}
