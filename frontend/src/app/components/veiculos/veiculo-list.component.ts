import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Veiculo } from '../../models/veiculo.model';
import { VeiculoService } from '../../services/veiculo.service';

@Component({
  selector: 'app-veiculo-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './veiculo-list.component.html',
  styleUrl: './veiculo-list.component.css'
})
export class VeiculoListComponent implements OnInit {

  veiculos: Veiculo[] = [];
  carregando = true;
  erro = '';

  constructor(private veiculoService: VeiculoService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.veiculoService.listar().subscribe({
      next: (dados) => {
        this.veiculos = dados;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Nao foi possivel carregar os veiculos.';
        this.carregando = false;
      }
    });
  }

  excluir(veiculo: Veiculo): void {
    if (!veiculo.id) return;
    if (!confirm(`Excluir o veiculo ${veiculo.modelo} (${veiculo.placa})?`)) return;

    this.veiculoService.excluir(veiculo.id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Nao foi possivel excluir o veiculo.')
    });
  }

}
