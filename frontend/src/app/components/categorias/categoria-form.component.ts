import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-categoria-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './categoria-form.component.html',
  styleUrl: './categoria-form.component.css'
})
export class CategoriaFormComponent implements OnInit {

  form = this.fb.group({
    nome: ['', Validators.required],
    tipo: ['CUSTO', Validators.required]
  });

  id: number | null = null;
  salvando = false;
  erro = '';

  constructor(
    private fb: FormBuilder,
    private categoriaService: CategoriaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = Number(idParam);
      this.categoriaService.buscarPorId(this.id).subscribe({
        next: (categoria) => this.form.patchValue(categoria),
        error: () => (this.erro = 'Nao foi possivel carregar a categoria.')
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando = true;
    const categoria = this.form.getRawValue() as any;

    const operacao = this.id
      ? this.categoriaService.atualizar(this.id, categoria)
      : this.categoriaService.criar(categoria);

    operacao.subscribe({
      next: () => this.router.navigate(['/categorias']),
      error: () => {
        this.erro = 'Nao foi possivel salvar a categoria.';
        this.salvando = false;
      }
    });
  }

}
