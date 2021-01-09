import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeographicalData } from 'app/shared/model/geographical-data.model';
import { GeographicalDataService } from './geographical-data.service';

@Component({
  templateUrl: './geographical-data-delete-dialog.component.html',
})
export class GeographicalDataDeleteDialogComponent {
  geographicalData?: IGeographicalData;

  constructor(
    protected geographicalDataService: GeographicalDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.geographicalDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('geographicalDataListModification');
      this.activeModal.close();
    });
  }
}
