import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeanScore } from 'app/shared/model/mean-score.model';

@Component({
  selector: 'jhi-mean-score-detail',
  templateUrl: './mean-score-detail.component.html'
})
export class MeanScoreDetailComponent implements OnInit {
  meanScore: IMeanScore | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meanScore }) => {
      this.meanScore = meanScore;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
