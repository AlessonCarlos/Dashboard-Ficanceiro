# Dashboard Financeiro para Motoristas de Aplicativo

Sistema de controle financeiro pessoal para motoristas de app (Uber, 99, etc.), com:

- **Backend:** Java 17 + Spring Boot 3.x + Spring Data JPA + H2 Database + Lombok
- **Frontend:** Angular 15+ + Reactive Forms + Chart.js
- **Exportação:** Excel (.xlsx) via Apache POI

## Estrutura

```
dashboard-financeiro/
├── backend/    # Spring Boot (API REST + persistência H2)
└── frontend/   # Angular (interface + gráficos)
```

## Status

🚧 Em desenvolvimento — projeto sendo construído fase a fase conforme cronograma (ver seção "Roadmap" abaixo).

## Pré-requisitos

- Java 17+
- Maven 3.9+ (ou usar o `mvnw` incluso, quando adicionado)
- Node.js 18+ e npm
- Angular CLI (`npm install -g @angular/cli`)

## Como executar

### Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. O console do H2 fica disponível em
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/dashboard-financeiro`,
usuário `sa`, senha em branco). O arquivo do banco é criado em `backend/data/`.

### Frontend (Angular)

```bash
cd frontend
npm install
ng serve
```

A aplicação sobe em `http://localhost:4200` e consome a API do backend em `:8080`.

## Roadmap

| Fase | Conteúdo |
|---|---|
| 0 | Setup inicial (Spring Boot + Angular + README) |
| 1 | Modelagem das entidades (Veículo, Conta, Categoria, Transação) |
| 2 | CRUD REST no backend |
| 3 | Serviços HTTP no frontend |
| 4 | Componentes de CRUD (listas + formulários) |
| 5 | Dashboard com gráficos (Chart.js) |
| 6 | Filtros avançados + exportação para Excel |
| 7 | Testes, ajustes finais e seed de dados |
