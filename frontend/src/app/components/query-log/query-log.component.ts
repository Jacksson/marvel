// query-log.component.ts

import { Component, OnInit } from '@angular/core';
import { format } from 'date-fns-tz';
import {QueryLogService} from "../../services/query-log.service";
import {QueryLog} from "../../models/log.model";

@Component({
  selector: 'app-query-log',
  templateUrl: './query-log.component.html',
  styleUrls: ['./query-log.component.css']
})
export class QueryLogComponent implements OnInit {
  queryLogs: QueryLog[] = [];

  constructor(private queryLogService: QueryLogService) { }

  ngOnInit(): void {
    this.loadQueryLogs();
  }

  private loadQueryLogs(): void {
    this.queryLogService.getQueryLogs().subscribe(
      (logs: QueryLog[]) => {
        this.queryLogs = logs;
      },
      (error) => {
        console.error('Error loading query logs:', error);
      }
    );
  }

  formatQueryTime(queryTime: string): string {
    return format(new Date(queryTime), 'yyyy-MM-dd HH:mm:ss', { timeZone: 'America/New_York' });
  }
}
