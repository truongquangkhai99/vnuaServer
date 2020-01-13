import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

import { HttpResponse } from '@angular/common/http';
import { HTeacherService } from 'app/home/hteacher/hteacher.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'jhi-hteacher',
  templateUrl: './hteacher.component.html',
  styleUrls: ['hteacher.scss']
})
export class HTeacherComponent implements OnInit, OnDestroy {
  teachers?: ITeacher[];
  account: Account | null = null;
  authSubscription?: Subscription;

  searchForm = this.formBuilder.group({
    query: ''
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private hteacherService: HTeacherService,
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
    this.hteacherService.customSearch(query).subscribe(
      (res: HttpResponse<ITeacher[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }
  protected onSuccess(data: ITeacher[] | null): void {
    this.teachers = data ? data : [];
  }
  protected onError(): void {
    //     console.log('test')
  }
}
