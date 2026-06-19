import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ApiService } from '../../services/api.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="page">
      <h3>Responses</h3>
      <div class="panel mb-3">
        <div class="row g-2">
          <div class="col"><input class="form-control" placeholder="Search name/email" [(ngModel)]="q"></div>
          <div class="col"><input class="form-control" type="date" [(ngModel)]="from"></div>
          <div class="col"><input class="form-control" type="date" [(ngModel)]="to"></div>
          <div class="col-auto"><button class="btn btn-primary" (click)="load()">Search</button></div>
          <div class="col-auto"><button class="btn btn-outline-success" (click)="exportCsv()">Export CSV</button></div>
        </div>
      </div>
      <div class="panel">
        <table class="table">
          <thead><tr><th>Name</th><th>Email</th><th>Survey</th><th>Submitted</th><th>Answers</th></tr></thead>
          <tbody>
            @for(r of rows; track r.id) {
              <tr><td>{{r.name}}</td><td>{{r.email}}</td><td>{{r.survey.title}}</td><td>{{r.submittedAt}}</td><td>{{r.answers.length}}</td></tr>
            }
          </tbody>
        </table>
      </div>
    </div>
  `
})
export class ResponseListComponent implements OnInit {
  rows: any[] = [];
  q = ''; from = ''; to = '';
  constructor(private api: ApiService, private http: HttpClient) {}
  ngOnInit() { this.load(); }
  load() {
    const params = new URLSearchParams();
    if (this.q) params.set('q', this.q);
    if (this.from) params.set('from', this.from);
    if (this.to) params.set('to', this.to);
    this.api.get<any[]>('/responses?' + params.toString()).subscribe(r => this.rows = r);
  }
  exportCsv() {
    const headers = new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token')}` });
    this.http.get('http://localhost:8080/api/responses/export', { headers, responseType: 'blob' }).subscribe(blob => {
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'survey-responses.csv';
      a.click();
      URL.revokeObjectURL(url);
    });
  }
}