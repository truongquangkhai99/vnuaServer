import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { VnuaServerSharedModule } from 'app/shared/shared.module';
import { VnuaServerCoreModule } from 'app/core/core.module';
import { VnuaServerAppRoutingModule } from './app-routing.module';
import { VnuaServerHomeModule } from './home/home.module';
import { VnuaServerEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

import { VnuaServerHTeacherModule } from './home/hteacher/hteacher.module';
@NgModule({
  imports: [
    BrowserModule,
    VnuaServerSharedModule,
    VnuaServerCoreModule,
    VnuaServerHomeModule,

    VnuaServerHTeacherModule,

    // jhipster-needle-angular-add-module JHipster will add new module here
    VnuaServerEntityModule,
    VnuaServerAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class VnuaServerAppModule {}
