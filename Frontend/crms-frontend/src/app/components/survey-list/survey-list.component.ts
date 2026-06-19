import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="page">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Surveys</h3>
        <a class="btn btn-primary" routerLink="/surveys/new">Create Survey</a>
      </div>
      <div class="panel">
        <table class="table table-hover">
          <thead><tr><th>Name</th><th>Status</th><th>Dates</th><th>Public Link</th><th></th></tr></thead>
          <tbody>
            @for(s of surveys; track s.id) {
              <tr>
                <td>{{s.title}}</td><td>{{s.status}}</td><td>{{s.startDate}} to {{s.endDate}}</td>
                <td><code>http://localhost:4200/survey/{{s.publicToken}}</code></td>
                <td>
                  <button class="btn btn-sm btn-success me-2" (click)="publish(s.id)" [disabled]="s.status==='PUBLISHED'">Publish</button>
                  <button class="btn btn-sm btn-outline-danger" (click)="remove(s.id)">Delete</button>
                </td>
              </tr>
            }
          </tbody>
        </table>
      </div>
    </div>
  `
})
export class SurveyListComponent implements OnInit {
  surveys: any[] = [];
  constructor(private api: ApiService) {}
  ngOnInit() { this.load(); }
  load() { this.api.get<any[]>('/surveys').subscribe(s => this.surveys = s); }
  publish(id: number) { this.api.put('/surveys/' + id + '/publish').subscribe(() => this.load()); }
  remove(id: number) { this.api.delete('/surveys/' + id).subscribe(() => this.load()); }
}