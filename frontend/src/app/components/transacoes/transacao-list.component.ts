import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Transacao } from '../../models/transacao.model';
import { TransacaoService } from '../../services/transacao.service';

@Component({
  selector: 'app-transacao-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './transacao-list.component.html',
  styleUrl: './transacao-list.component.css'
})
export class TransacaoListComponent implements OnInit {

  transacoes: Transacao[] = [];
  carregando = true;
  erro = '';

  constructor(private transacaoService: TransacaoService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.transacaoService.listar().subscribe({
      next: (dados) => {
        // mais recentes primeiro
        this.transacoes = [...dados].sort((a, b) => b.data.localeCompare(a.data));
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Nao foi possivel carregar as transacoes.';
        this.carregando = false;
      }
    });
  }

  excluir(transacao: Transacao): void {
    if (!transacao.id) return;
    if (!confirm(`Excluir a transacao "${transacao.descricao}"?`)) return;

    this.transacaoService.excluir(transacao.id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Nao foi possivel excluir a transacao.')
    });
  }

}
