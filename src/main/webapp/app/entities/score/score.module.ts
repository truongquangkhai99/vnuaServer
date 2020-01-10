import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VnuaServerSharedModule } from 'app/shared/shared.module';
import { ScoreComponent } from './score.component';
import { ScoreDetailComponent } from './score-detail.component';
import { ScoreUpdateComponent } from './score-update.component';
import { ScoreDeleteDialogComponent } from './score-delete-dialog.component';
import { scoreRoute } from './score.route';

@NgModule({
  imports: [VnuaServerSharedModule, RouterModule.forChild(scoreRoute)],
  declarations: [ScoreComponent, ScoreDetailComponent, ScoreUpdateComponent, ScoreDeleteDialogComponent],
  entryComponents: [ScoreDeleteDialogComponent]
})
export class VnuaServerScoreModule {}
