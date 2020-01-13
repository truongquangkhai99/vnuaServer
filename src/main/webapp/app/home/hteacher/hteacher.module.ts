import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VnuaServerSharedModule } from 'app/shared/shared.module';
import { HTEACHER_ROUTE } from './hteacher.route';
import { HTeacherComponent } from './hteacher.component';

@NgModule({
  imports: [VnuaServerSharedModule, RouterModule.forChild([HTEACHER_ROUTE])],
  declarations: [HTeacherComponent]
})
export class VnuaServerHTeacherModule {}
