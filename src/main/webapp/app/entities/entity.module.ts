import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.VnuaServerStudentModule)
      },
      {
        path: 'score',
        loadChildren: () => import('./score/score.module').then(m => m.VnuaServerScoreModule)
      },
      {
        path: 'mean-score',
        loadChildren: () => import('./mean-score/mean-score.module').then(m => m.VnuaServerMeanScoreModule)
      },
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.VnuaServerTeacherModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class VnuaServerEntityModule {}
