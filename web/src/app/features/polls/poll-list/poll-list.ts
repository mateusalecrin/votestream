import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Poll, PollStatus } from '../poll.model';
import { PollService } from '../poll.service';

@Component({
  selector: 'app-poll-list',
  imports: [RouterLink],
  templateUrl: './poll-list.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PollList {
  private readonly pollService = inject(PollService);

  protected readonly polls = signal<Poll[]>([]);
  protected readonly loading = signal(true);
  protected readonly error = signal<string | null>(null);

  constructor() {
    this.pollService.findAll().subscribe({
      next: (polls) => {
        this.polls.set(polls);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Não foi possível carregar as enquetes. Verifique se a API está rodando.');
        this.loading.set(false);
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
