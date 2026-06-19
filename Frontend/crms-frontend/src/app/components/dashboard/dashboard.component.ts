import { AfterViewInit, Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import Chart from 'chart.js/auto';

@Component({
  standalone: true,
  template: `
    <div class="page">
      <h3>Dashboard</h3>
      <div class="row g-3 my-2">
        <div class="col-md-3"><div class="metric"><b>{{data.totalSurveys || 0}}</b><br>Total Surveys</div></div>
        <div class="col-md-3"><div class="metric"><b>{{data.activeSurveys || 0}}</b><br>Active Surveys</div></div>
        <div class="col-md-3"><div class="metric"><b>{{data.expiredSurveys || 0}}</b><br>Expired Surveys</div></div>
        <div class="col-md-3"><div class="metric"><b>{{data.totalResponses || 0}}</b><br>Total Responses</div></div>
      </div>
      <div class="row g-3">
        <div class="col-md-8"><div class="panel"><canvas id="trend"></canvas></div></div>
        <div class="col-md-4"><div class="panel"><canvas id="pie"></canvas></div></div>
      </div>
    </div>
  `
})
export class DashboardComponent implements AfterViewInit {
  data: any = {};
  constructor(private api: ApiService) {}
  ngAfterViewInit() {
    this.api.get<any>('/dashboard').subscribe(d => {
      this.data = d;
      const labels = (d.trend || []).map((x: any[]) => x[0]);
      const counts = (d.trend || []).map((x: any[]) => x[1]);
      new Chart('trend', { type: 'bar', data: { labels, datasets: [{ label: 'Responses', data: counts, backgroundColor: '#0d6efd' }] } });
      new Chart('pie', { type: 'pie', data: { labels: ['Draft', 'Published'], datasets: [{ data: [d.surveyStatus?.draft || 0, d.surveyStatus?.published || 0], backgroundColor: ['#ffc107', '#198754'] }] } });
    });
  }
} 
