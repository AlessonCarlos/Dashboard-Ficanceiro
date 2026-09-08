import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Conta } from '../models/conta.model';
import { TipoConta } from '../models/enums';

@Injectable({
  providedIn: 'root'
})
export class ContaService {

  private readonly baseUrl = `${environment.apiUrl}/contas`;

  constructor(private http: HttpClient) {}

  listar(tipo?: TipoConta): Observable<Conta[]> {
    let params = new HttpParams();
    if (tipo) {
      params = params.set('tipo', tipo);
    }
    return this.http.get<Conta[]>(this.baseUrl, { params });
  }

  buscarPorId(id: number): Observable<Conta> {
    return this.http.get<Conta>(`${this.baseUrl}/${id}`);
  }

  criar(conta: Conta): Observable<Conta> {
    return this.http.post<Conta>(this.baseUrl, conta);
  }

  atualizar(id: number, conta: Conta): Observable<Conta> {
    return this.http.put<Conta>(`${this.baseUrl}/${id}`, conta);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

}
