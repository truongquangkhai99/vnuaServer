import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VnuaServerSharedModule } from 'app/shared/shared.module';
import { HSCORE_ROUTE } from './hscore.route';
import { HScoreComponent } from './hscore.component';

@NgModule({
  imports: [VnuaServerSharedModule, RouterModule.forChild([HSCORE_ROUTE])],
  declarations: [HScoreComponent]
})
export class VnuaServerHScoreModule {}
