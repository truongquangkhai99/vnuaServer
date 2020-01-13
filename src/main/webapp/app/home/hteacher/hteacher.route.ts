import { Route } from '@angular/router';

import { HTeacherComponent } from './hteacher.component';

export const HTEACHER_ROUTE: Route = {
  path: 'hteacher',
  component: HTeacherComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
};
