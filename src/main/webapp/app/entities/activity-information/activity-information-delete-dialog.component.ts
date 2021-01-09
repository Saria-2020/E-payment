import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityInformation } from 'app/shared/model/activity-information.model';
import { ActivityInformationService } from './activity-information.service';

@Component({
  templateUrl: './activity-information-delete-dialog.component.html',
})
export class ActivityInformationDeleteDialogComponent {
  activityInformation?: IActivityInformation;

  constructor(
    protected activityInformationService: ActivityInformationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activityInformationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('activityInformationListModification');
      this.activeModal.close();
    });
  }
}
