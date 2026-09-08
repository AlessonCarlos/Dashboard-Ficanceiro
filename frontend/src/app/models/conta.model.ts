import { TipoConta } from './enums';

export interface Conta {
  id?: number;
  nome: string;
  tipo: TipoConta;
}
