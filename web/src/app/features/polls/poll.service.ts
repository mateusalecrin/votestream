import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../../core/api.constants';
import { Poll, PollResults } from './poll.model';

export interface PollRequest {
  title: string;
  question: string;
  ownerId: number;
}

@Injectable({ providedIn: 'root' })
export class PollService {
  private readonly http = inject(HttpClient);

  findAll(): Observable<Poll[]> {
    return this.http.get<Poll[]>(`${API_BASE_URL}/polls`);
  }

  findById(id: number): Observable<Poll> {
    return this.http.get<Poll>(`${API_BASE_URL}/polls/${id}`);
  }

  create(request: PollRequest): Observable<Poll> {
    return this.http.post<Poll>(`${API_BASE_URL}/polls`, request);
  }

  getResults(pollId: number, userId: number): Observable<PollResults> {
    return this.http.get<PollResults>(`${API_BASE_URL}/polls/${pollId}/results`, {
      params: { userId },
    });
  }
}
