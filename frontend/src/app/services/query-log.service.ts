// query-log.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {QueryLog} from "../models/log.model";

@Injectable({
  providedIn: 'root'
})
export class QueryLogService {
  private apiUrl = '/api/log';

  constructor(private http: HttpClient) { }

  getQueryLogs(): Observable<QueryLog[]> {
    return this.http.get<QueryLog[]>(this.apiUrl);
  }
}
