import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';
import { TeacherComponent } from './teacher.component';
import { TeacherDetailComponent } from './teacher-detail.component';
import { TeacherUpdateComponent } from './teacher-update.component';

@Injectable({ providedIn: 'root' })
export class TeacherResolve implements Resolve<ITeacher> {
  constructor(private service: TeacherService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeacher> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((teacher: HttpResponse<Teacher>) => {
          if (teacher.body) {
            return of(teacher.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Teacher());
  }
}

export const teacherRoute: Routes = [
  {
    path: '',
    component: TeacherComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'vnuaServerApp.teacher.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TeacherDetailComponent,
    resolve: {
      teacher: TeacherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vnuaServerApp.teacher.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TeacherUpdateComponent,
    resolve: {
      teacher: TeacherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vnuaServerApp.teacher.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TeacherUpdateComponent,
    resolve: {
      teacher: TeacherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vnuaServerApp.teacher.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
