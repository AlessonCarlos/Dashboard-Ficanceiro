import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Conta } from '../../models/conta.model';
import { ContaService } from '../../services/conta.service';

@Component({
  selector: 'app-conta-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './conta-list.component.html',
  styleUrl: './conta-list.component.css'
})
export class ContaListComponent implements OnInit {

  contas: Conta[] = [];
  carregando = true;
  erro = '';

  constructor(private contaService: ContaService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.contaService.listar().subscribe({
      next: (dados) => {
        this.contas = dados;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Nao foi possivel carregar as contas.';
        this.carregando = false;
      }
    });
  }

  excluir(conta: Conta): void {
    if (!conta.id) return;
    if (!confirm(`Excluir a conta ${conta.nome}?`)) return;

    this.contaService.excluir(conta.id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Nao foi possivel excluir a conta.')
    });
  }

}
