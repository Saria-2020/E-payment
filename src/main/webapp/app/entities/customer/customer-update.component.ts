import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { RegisterService } from 'app/account/register/register.service';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [Validators.required],
    nationalId: [Validators.required],
    user: [],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    name: [Validators.required],
  });

  constructor(
    protected customerService: CustomerService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private registerService: RegisterService,
    private languageService: JhiLanguageService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      phoneNumber: customer.phoneNumber,
      nationalId: customer.nationalId,
      user: customer.user,
      email: customer.user?.email,
      name: customer.user?.firstName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  register(): void {
    if (this.editForm.get('id')!.value) {
      this.save();
    } else {
      const password = this.editForm.get(['phoneNumber'])!.value;
      const login = this.editForm.get(['phoneNumber'])!.value;
      const email = this.editForm.get(['email'])!.value;
      const firstName = this.editForm.get(['name'])!.value;
      this.registerService
        .save({ login, firstName, email, password, langKey: this.languageService.getCurrentLanguage() })
        .subscribe(response => {
          this.editForm.get('user')?.setValue(response.body);
          this.save();
        });
    }
  }

  save(): void {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      nationalId: this.editForm.get(['nationalId'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
