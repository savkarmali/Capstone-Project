import { Component } from '@angular/core';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <div class="page">
      <h3>Create Survey</h3>
      <form class="panel" [formGroup]="form" (ngSubmit)="save()">
        <input class="form-control mb-2" placeholder="Survey title" formControlName="title">
        <textarea class="form-control mb-2" placeholder="Description" formControlName="description"></textarea>
        <div class="row"><div class="col"><input class="form-control" type="date" formControlName="startDate"></div><div class="col"><input class="form-control" type="date" formControlName="endDate"></div></div>
        <hr>
        <div formArrayName="questions">
          @for(q of questions.controls; track $index; let i = $index) {
            <div class="border rounded p-3 mb-3" [formGroupName]="i">
              <input class="form-control mb-2" placeholder="Question text" formControlName="text">
              <select class="form-select mb-2" formControlName="type">
                <option>TEXT</option><option>TEXTAREA</option><option>RADIO</option><option>CHECKBOX</option><option>DATE</option><option>NUMBER</option>
              </select>
              <input class="form-control" placeholder="Options comma separated for radio/checkbox" formControlName="optionsText">
            </div>
          }
        </div>
        <button type="button" class="btn btn-outline-secondary me-2" (click)="add()" [disabled]="questions.length>=6">Add Question</button>
        <button class="btn btn-primary" [disabled]="form.invalid">Save Draft</button>
      </form>
    </div>
  `
})
export class SurveyFormComponent {
  form = this.fb.group({
    title: ['', Validators.required],
    description: [''],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    questions: this.fb.array([])
  });
  constructor(private fb: FormBuilder, private api: ApiService, private router: Router) { this.add(); }
  get questions() { return this.form.get('questions') as FormArray; }
  add() { this.questions.push(this.fb.group({ text: ['', Validators.required], type: ['TEXT', Validators.required], required: [true], optionsText: [''] })); }
  save() {
    const v: any = this.form.value;
    const body = { ...v, questions: v.questions.map((q: any) => ({ text: q.text, type: q.type, required: q.required, options: (q.optionsText || '').split(',').map((x: string) => x.trim()).filter(Boolean) })) };
    this.api.post('/surveys', body).subscribe(() => this.router.navigateByUrl('/surveys'));
  }
}