import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { VeiculoService } from '../../services/veiculo.service';

@Component({
  selector: 'app-veiculo-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './veiculo-form.component.html',
  styleUrl: './veiculo-form.component.css'
})
export class VeiculoFormComponent implements OnInit {

  form = this.fb.group({
    modelo: ['', Validators.required],
    placa: ['', Validators.required],
    ano: [new Date().getFullYear(), [Validators.required, Validators.min(1990)]],
    valorCompra: [0, [Validators.required, Validators.min(0)]],
    kmAtual: [0, [Validators.required, Validators.min(0)]],
    consumoMedio: [0, [Validators.required, Validators.min(0.01)]]
  });

  id: number | null = null;
  salvando = false;
  erro = '';

  constructor(
    private fb: FormBuilder,
    private veiculoService: VeiculoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = Number(idParam);
      this.veiculoService.buscarPorId(this.id).subscribe({
        next: (veiculo) => this.form.patchValue(veiculo),
        error: () => (this.erro = 'Nao foi possivel carregar o veiculo.')
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando = true;
    const veiculo = this.form.getRawValue() as any;

    const operacao = this.id
      ? this.veiculoService.atualizar(this.id, veiculo)
      : this.veiculoService.criar(veiculo);

    operacao.subscribe({
      next: () => this.router.navigate(['/veiculos']),
      error: () => {
        this.erro = 'Nao foi possivel salvar o veiculo. Confira os dados informados.';
        this.salvando = false;
      }
    });
  }

}
