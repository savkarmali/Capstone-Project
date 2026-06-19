import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <div class="page" style="max-width:760px">
      @if(done) { <div class="alert alert-success">Survey submitted successfully. Thank you for registering.</div> }
      @if(survey && !done) {
        <form class="panel" [formGroup]="form" (ngSubmit)="submit()">
          <h2>{{survey.title}}</h2><p>{{survey.description}}</p>
          <input class="form-control mb-2" placeholder="Your Name" formControlName="name">
          <input class="form-control mb-3" placeholder="Your Email" formControlName="email">
          @for(q of survey.questions; track q.id) {
            <div class="mb-3">
              <label class="form-label">{{q.text}} *</label>
              @if(q.type === 'TEXTAREA') { <textarea class="form-control" [formControlName]="'q'+q.id"></textarea> }
              @else if(q.type === 'DATE') { <input type="date" class="form-control" [formControlName]="'q'+q.id"> }
              @else if(q.type === 'NUMBER') { <input type="number" class="form-control" [formControlName]="'q'+q.id"> }
              @else if(q.type === 'RADIO') {
                @for(o of q.options; track o.id) { <div><input type="radio" [value]="o.labelValue" [formControlName]="'q'+q.id"> {{o.labelValue}}</div> }
              }
              @else if(q.type === 'CHECKBOX') {
                @for(o of q.options; track o.id) { <div><input type="checkbox" (change)="checkboxChanged(q.id, o.labelValue, $event)"> {{o.labelValue}}</div> }
              }
              @else { <input class="form-control" [formControlName]="'q'+q.id"> }
            </div>
          }
          @if(error) { <div class="alert alert-danger">{{error}}</div> }
          <button class="btn btn-primary" [disabled]="form.invalid">Submit Survey</button>
        </form>
      }
    </div>
  `
})
export class PublicSurveyComponent implements OnInit {
  survey: any;
  done = false;
  error = '';
  checked: Record<number, string[]> = {};
  form = this.fb.group({ name: ['', Validators.required], email: ['', [Validators.required, Validators.email]] } as any);
  constructor(private route: ActivatedRoute, private api: ApiService, private fb: FormBuilder) {}
  ngOnInit() {
    const token = this.route.snapshot.paramMap.get('token')!;
    this.api.get<any>('/public/surveys/' + token).subscribe(s => {
      this.survey = s;
      s.questions.forEach((q: any) => {
        this.form.addControl('q' + q.id, this.fb.control('', q.type === 'CHECKBOX' ? [] : Validators.required));
      });
    });
  }
  checkboxChanged(questionId: number, value: string, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    const current = this.checked[questionId] || [];
    this.checked[questionId] = checked ? [...current, value] : current.filter(x => x !== value);
  }
  submit() {
    const token = this.route.snapshot.paramMap.get('token')!;
    const raw: any = this.form.value;
    const missingCheckbox = this.survey.questions.find((q: any) => q.type === 'CHECKBOX' && !(this.checked[q.id] || []).length);
    if (missingCheckbox) {
      this.error = 'Please answer all mandatory questions';
      return;
    }
    const answers = this.survey.questions.map((q: any) => ({ questionId: q.id, answerText: q.type === 'CHECKBOX' ? (this.checked[q.id] || []).join('; ') : raw['q' + q.id] }));
    this.api.post('/public/surveys/' + token + '/responses', { name: raw.name, email: raw.email, answers }).subscribe({
      next: () => this.done = true,
      error: e => this.error = e.error?.message || 'Submission failed'
    });
  }
}