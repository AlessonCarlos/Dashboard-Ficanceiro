import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { EvolucaoMensal, GastoPorCategoria, SaldoResumo } from '../models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private readonly baseUrl = `${environment.apiUrl}/dashboard`;

  constructor(private http: HttpClient) {}

  obterResumo(): Observable<SaldoResumo> {
    return this.http.get<SaldoResumo>(`${this.baseUrl}/resumo`);
  }

  obterGastosPorCategoria(): Observable<GastoPorCategoria[]> {
    return this.http.get<GastoPorCategoria[]>(`${this.baseUrl}/gastos-por-categoria`);
  }

  obterEvolucaoMensal(): Observable<EvolucaoMensal[]> {
    return this.http.get<EvolucaoMensal[]>(`${this.baseUrl}/evolucao-mensal`);
  }

}
