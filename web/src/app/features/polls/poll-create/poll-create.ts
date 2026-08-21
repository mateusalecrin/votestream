import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { forkJoin, map, switchMap } from 'rxjs';

import { OptionService } from '../option.service';
import { PollService } from '../poll.service';

// TODO: substituir por um usuário real assim que existir tela de seleção/login.
const TEMP_OWNER_ID = 1;

@Component({
  selector: 'app-poll-create',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './poll-create.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PollCreate {
  private readonly fb = inject(FormBuilder);
  private readonly pollService = inject(PollService);
  private readonly optionService = inject(OptionService);
  private readonly router = inject(Router);

  protected readonly submitting = signal(false);
  protected readonly error = signal<string | null>(null);

  protected readonly form = this.fb.group({
    title: ['', Validators.required],
    question: ['', Validators.required],
    options: this.fb.array([this.fb.control('', Validators.required), this.fb.control('', Validators.required)]),
  });

  protected get options(): FormArray {
    return this.form.get('options') as FormArray;
  }

  protected addOption(): void {
    this.options.push(this.fb.control('', Validators.required));
  }

  protected removeOption(index: number): void {
    if (this.options.length > 2) {
      this.options.removeAt(index);
    }
  }

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting.set(true);
    this.error.set(null);

    const { title, question } = this.form.getRawValue();
    const optionTexts = (this.options.getRawValue() as string[]).filter((text) => text.trim().length > 0);

    this.pollService
      .create({ title: title!, question: question!, ownerId: TEMP_OWNER_ID })
      .pipe(
        switchMap((poll) =>
          forkJoin(optionTexts.map((text) => this.optionService.create(poll.id, text))).pipe(map(() => poll)),
        ),
      )
      .subscribe({
        next: (poll) => this.router.navigate(['/polls', poll.id]),
        error: () => {
          this.error.set('Não foi possível criar a enquete. Verifique os dados e tente novamente.');
          this.submitting.set(false);
        },
      });
  }
}
