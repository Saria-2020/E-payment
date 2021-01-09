import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPaymentSharedModule } from 'app/shared/shared.module';
import { PaymentInfoComponent } from './payment-info.component';
import { PaymentInfoDetailComponent } from './payment-info-detail.component';
import { PaymentInfoUpdateComponent } from './payment-info-update.component';
import { PaymentInfoDeleteDialogComponent } from './payment-info-delete-dialog.component';
import { paymentInfoRoute } from './payment-info.route';

@NgModule({
  imports: [EPaymentSharedModule, RouterModule.forChild(paymentInfoRoute)],
  declarations: [PaymentInfoComponent, PaymentInfoDetailComponent, PaymentInfoUpdateComponent, PaymentInfoDeleteDialogComponent],
  entryComponents: [PaymentInfoDeleteDialogComponent],
})
export class EPaymentPaymentInfoModule {}
