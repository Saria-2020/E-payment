import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IActivityInformation, ActivityInformation } from 'app/shared/model/activity-information.model';
import { ActivityInformationService } from './activity-information.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

type SelectableEntity = ICustomer | ICategory;

@Component({
  selector: 'jhi-activity-information-update',
  templateUrl: './activity-information-update.component.html',
})
export class ActivityInformationUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    sector: [],
    typeOfActivity: [],
    propertyType: [],
    areaClass: [],
    architectureType: [],
    numberOfFloors: [],
    features: [],
    descriptionOfTheFeatures: [],
    customer: [],
    category: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected activityInformationService: ActivityInformationService,
    protected customerService: CustomerService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityInformation }) => {
      this.updateForm(activityInformation);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(activityInformation: IActivityInformation): void {
    this.editForm.patchValue({
      id: activityInformation.id,
      name: activityInformation.name,
      sector: activityInformation.sector,
      typeOfActivity: activityInformation.typeOfActivity,
      propertyType: activityInformation.propertyType,
      areaClass: activityInformation.areaClass,
      architectureType: activityInformation.architectureType,
      numberOfFloors: activityInformation.numberOfFloors,
      features: activityInformation.features,
      descriptionOfTheFeatures: activityInformation.descriptionOfTheFeatures,
      customer: activityInformation.customer,
      category: activityInformation.category,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ePaymentApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activityInformation = this.createFromForm();
    if (activityInformation.id !== undefined) {
      this.subscribeToSaveResponse(this.activityInformationService.update(activityInformation));
    } else {
      this.subscribeToSaveResponse(this.activityInformationService.create(activityInformation));
    }
  }

  private createFromForm(): IActivityInformation {
    return {
      ...new ActivityInformation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      sector: this.editForm.get(['sector'])!.value,
      typeOfActivity: this.editForm.get(['typeOfActivity'])!.value,
      propertyType: this.editForm.get(['propertyType'])!.value,
      areaClass: this.editForm.get(['areaClass'])!.value,
      architectureType: this.editForm.get(['architectureType'])!.value,
      numberOfFloors: this.editForm.get(['numberOfFloors'])!.value,
      features: this.editForm.get(['features'])!.value,
      descriptionOfTheFeatures: this.editForm.get(['descriptionOfTheFeatures'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityInformation>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
