import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../../core/api.constants';
import { OptionSummary } from './poll.model';

@Injectable({ providedIn: 'root' })
export class OptionService {
  private readonly http = inject(HttpClient);

  create(pollId: number, text: string): Observable<OptionSummary> {
    return this.http.post<OptionSummary>(`${API_BASE_URL}/polls/${pollId}/options`, { text });
  }
}
