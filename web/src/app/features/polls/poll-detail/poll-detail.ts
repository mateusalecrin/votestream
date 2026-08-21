import { ChangeDetectionStrategy, Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { PollResults, PollStatus } from '../poll.model';
import { PollService } from '../poll.service';
import { VoteService } from '../vote.service';

// TODO: substituir por um usuário real assim que existir tela de seleção/login.
const TEMP_USER_ID = 1;

@Component({
  selector: 'app-poll-detail',
  imports: [RouterLink],
  templateUrl: './poll-detail.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PollDetail {
  private readonly route = inject(ActivatedRoute);
  private readonly pollService = inject(PollService);
  private readonly voteService = inject(VoteService);

  private readonly pollId = Number(this.route.snapshot.paramMap.get('id'));

  protected readonly results = signal<PollResults | null>(null);
  protected readonly loading = signal(true);
  protected readonly error = signal<string | null>(null);
  protected readonly selectedOptionId = signal<number | null>(null);
  protected readonly voting = signal(false);

  protected readonly totalVotes = computed(() => {
    const results = this.results();
    return results ? results.options.reduce((sum, option) => sum + option.voteCount, 0) : 0;
  });

  constructor() {
    this.loadResults();
  }

  private loadResults(): void {
    this.loading.set(true);
    this.pollService.getResults(this.pollId, TEMP_USER_ID).subscribe({
      next: (results) => {
        this.results.set(results);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Não foi possível carregar esta enquete.');
        this.loading.set(false);
      },
    });
  }

  protected percentage(count: number): number {
    const total = this.totalVotes();
    return total === 0 ? 0 : Math.round((count / total) * 100);
  }

  protected vote(): void {
    const optionId = this.selectedOptionId();
    if (optionId === null) {
      return;
    }

    this.voting.set(true);
    this.voteService.cast({ pollId: this.pollId, optionId, userId: TEMP_USER_ID }).subscribe({
      next: () => {
        this.voting.set(false);
        this.loadResults();
      },
      error: () => {
        this.error.set('Não foi possível registrar seu voto.');
        this.voting.set(false);
      },
    });
  }

  protected statusClass(status: PollStatus): string {
    switch (status) {
      case 'OPEN':
        return 'bg-emerald-100 text-emerald-700';
      case 'CLOSED':
        return 'bg-slate-100 text-slate-600';
      case 'CANCELLED':
        return 'bg-rose-100 text-rose-700';
    }
  }
}
