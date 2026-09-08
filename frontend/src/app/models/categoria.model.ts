import { TipoMovimento } from './enums';

export interface Categoria {
  id?: number;
  nome: string;
  tipo: TipoMovimento;
}
