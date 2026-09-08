import { Categoria } from './categoria.model';
import { Conta } from './conta.model';
import { TipoMovimento } from './enums';
import { Veiculo } from './veiculo.model';

export interface Transacao {
  id?: number;
  conta: Conta;
  veiculo?: Veiculo;
  categoria: Categoria;
  descricao: string;
  valor: number;
  data: string; // formato yyyy-MM-dd, igual ao @JsonFormat do backend
  tipo: TipoMovimento;
}
