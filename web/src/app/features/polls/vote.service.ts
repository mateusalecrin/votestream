import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../../core/api.constants';

export interface VoteRequest {
  pollId: number;
  optionId: number;
  userId: number;
}

export interface Vote {
  id: number;
  pollId: number;
  optionId: number;
  optionText: string;
  userId: number;
  userName: string;
  votedAt: string;
}

@Injectable({ providedIn: 'root' })
export class VoteService {
  private readonly http = inject(HttpClient);

  cast(request: VoteRequest): Observable<Vote> {
    return this.http.post<Vote>(`${API_BASE_URL}/votes`, request);
  }
}
