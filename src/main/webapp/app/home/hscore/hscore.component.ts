import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

import { HttpResponse } from '@angular/common/http';
import { HScoreService } from 'app/home/hscore/hscore.service';

import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'jhi-score',
  templateUrl: './hscore.component.html',
  styleUrls: ['hscore.scss']
})
export class HScoreComponent implements OnInit, OnDestroy {
  topStudents?: any[];
  account: Account | null = null;
  authSubscription?: Subscription;

  searchForm = this.formBuilder.group({
    query: ''
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private hScoreService: HScoreService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.getTopStudents();
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

  getTopStudents(): void {
    this.hScoreService.getTopStudents().subscribe(
      (res: HttpResponse<any[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }
  protected onSuccess(data: any[] | null): void {
    this.topStudents = data ? data : [];
  }

  protected onError(): void {
    //     console.log('test')
  }
}
