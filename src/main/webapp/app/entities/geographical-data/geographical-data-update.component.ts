import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGeographicalData, GeographicalData } from 'app/shared/model/geographical-data.model';
import { GeographicalDataService } from './geographical-data.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-geographical-data-update',
  templateUrl: './geographical-data-update.component.html',
})
export class GeographicalDataUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    state: [],
    units: [],
    district: [],
    square: [],
    realEstateNumber: [],
    activityNumber: [],
    areaOfTheRealEstate: [],
    customer: [],
  });

  constructor(
    protected geographicalDataService: GeographicalDataService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geographicalData }) => {
      this.updateForm(geographicalData);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(geographicalData: IGeographicalData): void {
    this.editForm.patchValue({
      id: geographicalData.id,
      state: geographicalData.state,
      units: geographicalData.units,
      district: geographicalData.district,
      square: geographicalData.square,
      realEstateNumber: geographicalData.realEstateNumber,
      activityNumber: geographicalData.activityNumber,
      areaOfTheRealEstate: geographicalData.areaOfTheRealEstate,
      customer: geographicalData.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const geographicalData = this.createFromForm();
    if (geographicalData.id !== undefined) {
      this.subscribeToSaveResponse(this.geographicalDataService.update(geographicalData));
    } else {
      this.subscribeToSaveResponse(this.geographicalDataService.create(geographicalData));
    }
  }

  private createFromForm(): IGeographicalData {
    return {
      ...new GeographicalData(),
      id: this.editForm.get(['id'])!.value,
      state: this.editForm.get(['state'])!.value,
      units: this.editForm.get(['units'])!.value,
      district: this.editForm.get(['district'])!.value,
      square: this.editForm.get(['square'])!.value,
      realEstateNumber: this.editForm.get(['realEstateNumber'])!.value,
      activityNumber: this.editForm.get(['activityNumber'])!.value,
      areaOfTheRealEstate: this.editForm.get(['areaOfTheRealEstate'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeographicalData>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
