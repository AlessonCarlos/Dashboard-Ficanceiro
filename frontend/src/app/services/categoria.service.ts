import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Categoria } from '../models/categoria.model';
import { TipoMovimento } from '../models/enums';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private readonly baseUrl = `${environment.apiUrl}/categorias`;

  constructor(private http: HttpClient) {}

  listar(tipo?: TipoMovimento): Observable<Categoria[]> {
    let params = new HttpParams();
    if (tipo) {
      params = params.set('tipo', tipo);
    }
    return this.http.get<Categoria[]>(this.baseUrl, { params });
  }

  buscarPorId(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.baseUrl}/${id}`);
  }

  criar(categoria: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.baseUrl, categoria);
  }

  atualizar(id: number, categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.baseUrl}/${id}`, categoria);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

}
