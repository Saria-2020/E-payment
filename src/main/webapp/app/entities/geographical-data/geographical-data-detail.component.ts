import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeographicalData } from 'app/shared/model/geographical-data.model';

@Component({
  selector: 'jhi-geographical-data-detail',
  templateUrl: './geographical-data-detail.component.html',
})
export class GeographicalDataDetailComponent implements OnInit {
  geographicalData: IGeographicalData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geographicalData }) => (this.geographicalData = geographicalData));
  }

  previousState(): void {
    window.history.back();
  }
}
