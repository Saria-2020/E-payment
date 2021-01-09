import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IActivityInformation } from 'app/shared/model/activity-information.model';

@Component({
  selector: 'jhi-activity-information-detail',
  templateUrl: './activity-information-detail.component.html',
})
export class ActivityInformationDetailComponent implements OnInit {
  activityInformation: IActivityInformation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityInformation }) => (this.activityInformation = activityInformation));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
