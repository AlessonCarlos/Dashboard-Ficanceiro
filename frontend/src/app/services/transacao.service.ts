import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { TipoConta, TipoMovimento } from '../models/enums';
import { Transacao } from '../models/transacao.model';

export interface FiltrosTransacao {
  dataInicio?: string;
  dataFim?: string;
  categoriaId?: number;
  contaId?: number;
  tipoConta?: TipoConta;
  tipo?: TipoMovimento;
}

@Injectable({
  providedIn: 'root'
})
export class TransacaoService {

  private readonly baseUrl = `${environment.apiUrl}/transacoes`;

  constructor(private http: HttpClient) {}

  listar(filtros?: FiltrosTransacao): Observable<Transacao[]> {
    let params = new HttpParams();
    if (filtros) {
      Object.entries(filtros).forEach(([chave, valor]) => {
        if (valor !== undefined && valor !== null && valor !== '') {
          params = params.set(chave, String(valor));
        }
      });
    }
    return this.http.get<Transacao[]>(this.baseUrl, { params });
  }

  buscarPorId(id: number): Observable<Transacao> {
    return this.http.get<Transacao>(`${this.baseUrl}/${id}`);
  }

  criar(transacao: Transacao): Observable<Transacao> {
    return this.http.post<Transacao>(this.baseUrl, transacao);
  }

  atualizar(id: number, transacao: Transacao): Observable<Transacao> {
    return this.http.put<Transacao>(`${this.baseUrl}/${id}`, transacao);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

}
