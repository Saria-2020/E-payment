import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPaymentSharedModule } from 'app/shared/shared.module';
import { InvoiceItemComponent } from './invoice-item.component';
import { InvoiceItemDetailComponent } from './invoice-item-detail.component';
import { InvoiceItemUpdateComponent } from './invoice-item-update.component';
import { InvoiceItemDeleteDialogComponent } from './invoice-item-delete-dialog.component';
import { invoiceItemRoute } from './invoice-item.route';

@NgModule({
  imports: [EPaymentSharedModule, RouterModule.forChild(invoiceItemRoute)],
  declarations: [InvoiceItemComponent, InvoiceItemDetailComponent, InvoiceItemUpdateComponent, InvoiceItemDeleteDialogComponent],
  entryComponents: [InvoiceItemDeleteDialogComponent],
})
export class EPaymentInvoiceItemModule {}
