import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

import { HttpResponse } from '@angular/common/http';
import { StudentService } from 'app/entities/student/student.service';
import { IStudent } from 'app/shared/model/student.model';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  students?: IStudent[];
  account: Account | null = null;
  authSubscription?: Subscription;

  searchForm = this.formBuilder.group({
    query: ''
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private studentService: StudentService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  search(): void {
    const query = this.searchForm.value.query;
    this.studentService.customSearch(query).subscribe(
      (res: HttpResponse<IStudent[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }
  protected onSuccess(data: IStudent[] | null): void {
    this.students = data ? data : [];
  }
  protected onError(): void {
    //     console.log('test')
  }
}
