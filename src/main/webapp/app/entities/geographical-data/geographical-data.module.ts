import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPaymentSharedModule } from 'app/shared/shared.module';
import { GeographicalDataComponent } from './geographical-data.component';
import { GeographicalDataDetailComponent } from './geographical-data-detail.component';
import { GeographicalDataUpdateComponent } from './geographical-data-update.component';
import { GeographicalDataDeleteDialogComponent } from './geographical-data-delete-dialog.component';
import { geographicalDataRoute } from './geographical-data.route';

@NgModule({
  imports: [EPaymentSharedModule, RouterModule.forChild(geographicalDataRoute)],
  declarations: [
    GeographicalDataComponent,
    GeographicalDataDetailComponent,
    GeographicalDataUpdateComponent,
    GeographicalDataDeleteDialogComponent,
  ],
  entryComponents: [GeographicalDataDeleteDialogComponent],
})
export class EPaymentGeographicalDataModule {}
