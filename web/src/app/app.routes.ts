import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'polls', pathMatch: 'full' },
  {
    path: 'polls',
    loadComponent: () => import('./features/polls/poll-list/poll-list').then((m) => m.PollList),
  },
  {
    path: 'polls/new',
    loadComponent: () => import('./features/polls/poll-create/poll-create').then((m) => m.PollCreate),
  },
  {
    path: 'polls/:id',
    loadComponent: () => import('./features/polls/poll-detail/poll-detail').then((m) => m.PollDetail),
  },
];
