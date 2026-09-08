export interface SaldoResumo {
  totalGanhos: number;
  totalCustos: number;
  saldo: number;
}

export interface GastoPorCategoria {
  categoria: string;
  total: number;
}

export interface EvolucaoMensal {
  mes: string;
  ganhos: number;
  custos: number;
}
