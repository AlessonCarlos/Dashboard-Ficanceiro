import { Routes } from '@angular/router';
import { CategoriaFormComponent } from './components/categorias/categoria-form.component';
import { CategoriaListComponent } from './components/categorias/categoria-list.component';
import { ContaFormComponent } from './components/contas/conta-form.component';
import { ContaListComponent } from './components/contas/conta-list.component';
import { TransacaoFormComponent } from './components/transacoes/transacao-form.component';
import { TransacaoListComponent } from './components/transacoes/transacao-list.component';
import { VeiculoFormComponent } from './components/veiculos/veiculo-form.component';
import { VeiculoListComponent } from './components/veiculos/veiculo-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'transacoes', pathMatch: 'full' },

  { path: 'veiculos', component: VeiculoListComponent },
  { path: 'veiculos/novo', component: VeiculoFormComponent },
  { path: 'veiculos/:id/editar', component: VeiculoFormComponent },

  { path: 'contas', component: ContaListComponent },
  { path: 'contas/novo', component: ContaFormComponent },
  { path: 'contas/:id/editar', component: ContaFormComponent },

  { path: 'categorias', component: CategoriaListComponent },
  { path: 'categorias/novo', component: CategoriaFormComponent },
  { path: 'categorias/:id/editar', component: CategoriaFormComponent },

  { path: 'transacoes', component: TransacaoListComponent },
  { path: 'transacoes/novo', component: TransacaoFormComponent },
  { path: 'transacoes/:id/editar', component: TransacaoFormComponent },

  { path: '**', redirectTo: 'transacoes' }
];
