import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ContaService } from '../../services/conta.service';

@Component({
  selector: 'app-conta-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './conta-form.component.html',
  styleUrl: './conta-form.component.css'
})
export class ContaFormComponent implements OnInit {

  form = this.fb.group({
    nome: ['', Validators.required],
    tipo: ['TRABALHO', Validators.required]
  });

  id: number | null = null;
  salvando = false;
  erro = '';

  constructor(
    private fb: FormBuilder,
    private contaService: ContaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = Number(idParam);
      this.contaService.buscarPorId(this.id).subscribe({
        next: (conta) => this.form.patchValue(conta),
        error: () => (this.erro = 'Nao foi possivel carregar a conta.')
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando = true;
    const conta = this.form.getRawValue() as any;

    const operacao = this.id
      ? this.contaService.atualizar(this.id, conta)
      : this.contaService.criar(conta);

    operacao.subscribe({
      next: () => this.router.navigate(['/contas']),
      error: () => {
        this.erro = 'Nao foi possivel salvar a conta.';
        this.salvando = false;
      }
    });
  }

}
