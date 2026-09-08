import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Categoria } from '../../models/categoria.model';
import { Conta } from '../../models/conta.model';
import { Veiculo } from '../../models/veiculo.model';
import { CategoriaService } from '../../services/categoria.service';
import { ContaService } from '../../services/conta.service';
import { TransacaoService } from '../../services/transacao.service';
import { VeiculoService } from '../../services/veiculo.service';

@Component({
  selector: 'app-transacao-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './transacao-form.component.html',
  styleUrl: './transacao-form.component.css'
})
export class TransacaoFormComponent implements OnInit {

  form = this.fb.group({
    contaId: [null as number | null, Validators.required],
    categoriaId: [null as number | null, Validators.required],
    veiculoId: [null as number | null],
    descricao: ['', Validators.required],
    valor: [0, [Validators.required, Validators.min(0.01)]],
    data: [new Date().toISOString().substring(0, 10), Validators.required],
    tipo: ['CUSTO', Validators.required]
  });

  contas: Conta[] = [];
  categorias: Categoria[] = [];
  veiculos: Veiculo[] = [];

  id: number | null = null;
  salvando = false;
  erro = '';

  constructor(
    private fb: FormBuilder,
    private transacaoService: TransacaoService,
    private contaService: ContaService,
    private categoriaService: CategoriaService,
    private veiculoService: VeiculoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.contaService.listar().subscribe((dados) => (this.contas = dados));
    this.categoriaService.listar().subscribe((dados) => (this.categorias = dados));
    this.veiculoService.listar().subscribe((dados) => (this.veiculos = dados));

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = Number(idParam);
      this.transacaoService.buscarPorId(this.id).subscribe({
        next: (transacao) =>
          this.form.patchValue({
            contaId: transacao.conta.id ?? null,
            categoriaId: transacao.categoria.id ?? null,
            veiculoId: transacao.veiculo?.id ?? null,
            descricao: transacao.descricao,
            valor: transacao.valor,
            data: transacao.data,
            tipo: transacao.tipo
          }),
        error: () => (this.erro = 'Nao foi possivel carregar a transacao.')
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando = true;
    const valores = this.form.getRawValue();

    const transacao: any = {
      conta: { id: valores.contaId },
      categoria: { id: valores.categoriaId },
      veiculo: valores.veiculoId ? { id: valores.veiculoId } : undefined,
      descricao: valores.descricao,
      valor: valores.valor,
      data: valores.data,
      tipo: valores.tipo
    };

    const operacao = this.id
      ? this.transacaoService.atualizar(this.id, transacao)
      : this.transacaoService.criar(transacao);

    operacao.subscribe({
      next: () => this.router.navigate(['/transacoes']),
      error: () => {
        this.erro = 'Nao foi possivel salvar a transacao. Confira os dados informados.';
        this.salvando = false;
      }
    });
  }

}
