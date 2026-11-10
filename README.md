# Extensão UnB — Backend

API REST em Spring Boot (Java 21) para gerenciar projetos, oportunidades e eventos
de extensão da UnB. Banco de dados MongoDB.

Este é o repositório do **backend**. O `docker compose` deste repositório sobe o
sistema inteiro (banco, backend e frontend) — veja
[Estrutura de pastas necessária](#estrutura-de-pastas-necessária) antes de rodar.

## Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/) e Docker Compose (já vem junto no Docker Desktop / Docker Engine recente)
- Git

Não precisa ter Java, Maven nem Node instalados na máquina — tudo isso roda dentro
dos containers.

## Estrutura de pastas necessária

O `compose.yml` builda o frontend a partir de um caminho relativo (`../../extensao-unb-front`),
então os dois repositórios **precisam estar lado a lado**, com esses nomes exatos:

```
uma-pasta-qualquer/
├── extensao-unb-back/
│   └── backend/          ← você está aqui, é daqui que roda o docker compose
└── extensao-unb-front/
```

```bash
mkdir projeto-tcc && cd projeto-tcc
git clone <url-do-repositorio-backend> extensao-unb-back
git clone <url-do-repositorio-frontend> extensao-unb-front
cd extensao-unb-back/backend
```

## Configuração

Copie o arquivo de exemplo e preencha os valores:

```bash
cp .env.example .env
```

Edite o `.env` gerado — os comentários no próprio arquivo explicam cada variável.
Nunca commite o `.env` (já está no `.gitignore`).

## Rodando tudo

```bash
docker compose up --build
```

Na primeira vez demora alguns minutos (baixa as imagens base e instala as
dependências do zero). Isso sobe, na ordem certa, esperando cada serviço ficar
pronto antes de liberar o próximo:

| Serviço | O que é | Acesso |
|---|---|---|
| `mongo` | Banco de dados | interno (porta 27017 também publicada, útil pra inspecionar) |
| `mailpit` | SMTP falso, pra ver e-mails de verificação sem precisar de conta real | http://localhost:8025 |
| `mongo-express` | UI web pra navegar no banco | http://localhost:8081 |
| `backend` | Esta API | http://localhost:8080 |
| `frontend` | Site (Nuxt) — **use este pra navegar no site** | http://localhost:3000 |

O navegador só fala com o frontend (`:3000`). Chamadas que o site faz pra API
(`/api/**`) são repassadas internamente pelo próprio Nuxt pro backend, pela
rede do Docker — não existe proxy separado nem porta 80 nesse fluxo local.

Pra rodar em segundo plano (sem prender o terminal): `docker compose up --build -d`.
Pra ver os logs depois: `docker compose logs -f` (ou `docker compose logs -f backend`
pra só um serviço). Pra derrubar tudo: `docker compose down`.

## Populando o banco com dados de teste

O banco sobe vazio. Pra ter projetos, professores e alunos de exemplo pra testar:

```bash
docker exec -i backend-mongo-1 mongosh -u "$MONGO_USER" -p "$MONGO_PASSWORD" --authenticationDatabase admin < seed/mongo-seed.js
```

(as variáveis `$MONGO_USER`/`$MONGO_PASSWORD` precisam estar no seu shell — rode
`set -a && source .env && set +a` antes se não estiverem)

Depois disso, dá pra logar com qualquer e-mail do seed e a senha `senha123456` —
por exemplo `ana.paula@unb.br` (professora) ou `gabriela.naoseidasquantas@aluno.unb.br`
(aluna). Mais detalhes em `seed/IMPORT_GUIDE.md`.

## Documentação da API

Com o backend no ar: http://localhost:8080/swagger-ui.html

## Arquitetura — decisões que valem explicar

- **Multi-stage build** no `Dockerfile`: uma etapa com Maven+JDK completos só pra
  compilar, e a imagem final só com o `.jar` + um JRE — bem mais enxuta.
- **`spring.mongodb.uri`, não `spring.data.mongodb.uri`**: nesta versão do Spring
  Boot (4.0.5) o prefixo de propriedade da conexão Mongo mudou. Usar o nome antigo
  não dá erro nenhum — só é silenciosamente ignorado, e a app conecta em
  `localhost` sem credencial, sem avisar.
- **`spring.data.mongodb.auto-index-creation=false`**: rodando como `.jar`
  empacotado (é sempre assim dentro de um container), a criação automática de
  índices no boot acontece rápido demais e corre com a autenticação da conexão
  ainda não ter terminado de negociar — o Mongo recusa com "Unauthorized" e a
  aplicação inteira falha ao subir. Os índices que a aplicação usa já são criados
  manualmente pelo script de seed.
- **Proxy interno no frontend, não um proxy reverso separado**: o navegador só
  fala com a origem do frontend; uma `routeRule` de proxy no `nuxt.config.ts`
  repassa `/api/**` pro backend pela rede interna do Docker. Evita CORS e
  problemas de "mixed content" quando o frontend tem HTTPS e o backend não tem
  domínio próprio (como no deploy via Coolify, onde só o frontend recebeu um
  domínio real).
- **`POST /projetos/importar`**: rota pública (sem login), protegida por um
  header `X-Import-Key` em vez de autenticação normal — pensada pra scripts
  externos importarem projetos em massa sem precisar criar conta pra cada
  coordenador. Ver `IMPORT_API_KEY` no `.env`.

## Rodando sem Docker (desenvolvimento)

```bash
./mvnw spring-boot:run
```

Isso também sobe o `mongo`/`mailpit`/`mongo-express` automaticamente via Docker
Compose (integração nativa do Spring Boot), mas roda o backend direto na sua
máquina — mais rápido pra iterar durante o desenvolvimento.

```bash
./mvnw test                              # todos os testes
./mvnw test -Dtest=NomeDaClasse          # uma classe especifica
```
