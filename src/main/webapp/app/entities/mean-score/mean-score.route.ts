import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeanScore, MeanScore } from 'app/shared/model/mean-score.model';
import { MeanScoreService } from './mean-score.service';
import { MeanScoreComponent } from './mean-score.component';
import { MeanScoreDetailComponent } from './mean-score-detail.component';
import { MeanScoreUpdateComponent } from './mean-score-update.component';

@Injectable({ providedIn: 'root' })
export class MeanScoreResolve implements Resolve<IMeanScore> {
  constructor(private service: MeanScoreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeanScore> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((meanScore: HttpResponse<MeanScore>) => {
          if (meanScore.body) {
            return of(meanScore.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MeanScore());
  }
}

export const meanScoreRoute: Routes = [
  {
    path: '',
    component: MeanScoreComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'vnuaServerApp.meanScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeanScoreDetailComponent,
    resolve: {
      meanScore: MeanScoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vnuaServerApp.meanScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeanScoreUpdateComponent,
    resolve: {
      meanScore: MeanScoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vnuaServerApp.meanScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeanScoreUpdateComponent,
    resolve: {
      meanScore: MeanScoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vnuaServerApp.meanScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
