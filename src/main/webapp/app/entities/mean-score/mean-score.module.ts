import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VnuaServerSharedModule } from 'app/shared/shared.module';
import { MeanScoreComponent } from './mean-score.component';
import { MeanScoreDetailComponent } from './mean-score-detail.component';
import { MeanScoreUpdateComponent } from './mean-score-update.component';
import { MeanScoreDeleteDialogComponent } from './mean-score-delete-dialog.component';
import { meanScoreRoute } from './mean-score.route';

@NgModule({
  imports: [VnuaServerSharedModule, RouterModule.forChild(meanScoreRoute)],
  declarations: [MeanScoreComponent, MeanScoreDetailComponent, MeanScoreUpdateComponent, MeanScoreDeleteDialogComponent],
  entryComponents: [MeanScoreDeleteDialogComponent]
})
export class VnuaServerMeanScoreModule {}
