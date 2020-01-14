import { Route } from '@angular/router';

import { HScoreComponent } from './hscore.component';

export const HSCORE_ROUTE: Route = {
  path: 'hscore',
  component: HScoreComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
};
