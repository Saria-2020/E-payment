import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPaymentSharedModule } from 'app/shared/shared.module';
import { ActivityInformationComponent } from './activity-information.component';
import { ActivityInformationDetailComponent } from './activity-information-detail.component';
import { ActivityInformationUpdateComponent } from './activity-information-update.component';
import { ActivityInformationDeleteDialogComponent } from './activity-information-delete-dialog.component';
import { activityInformationRoute } from './activity-information.route';

@NgModule({
  imports: [EPaymentSharedModule, RouterModule.forChild(activityInformationRoute)],
  declarations: [
    ActivityInformationComponent,
    ActivityInformationDetailComponent,
    ActivityInformationUpdateComponent,
    ActivityInformationDeleteDialogComponent,
  ],
  entryComponents: [ActivityInformationDeleteDialogComponent],
})
export class EPaymentActivityInformationModule {}
