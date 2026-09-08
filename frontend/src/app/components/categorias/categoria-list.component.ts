import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Categoria } from '../../models/categoria.model';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-categoria-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './categoria-list.component.html',
  styleUrl: './categoria-list.component.css'
})
export class CategoriaListComponent implements OnInit {

  categorias: Categoria[] = [];
  carregando = true;
  erro = '';

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.categoriaService.listar().subscribe({
      next: (dados) => {
        this.categorias = dados;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Nao foi possivel carregar as categorias.';
        this.carregando = false;
      }
    });
  }

  excluir(categoria: Categoria): void {
    if (!categoria.id) return;
    if (!confirm(`Excluir a categoria ${categoria.nome}?`)) return;

    this.categoriaService.excluir(categoria.id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Nao foi possivel excluir a categoria.')
    });
  }

}
