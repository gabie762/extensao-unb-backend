// Seed script aligned with the improved mocks.
// Usage:
// sudo docker compose up -d
// sudo docker exec -i backend-mongo-1 mongosh -u "$MONGO_USER" -p "$MONGO_PASSWORD" --authenticationDatabase admin < seed/mongo-seed.js

const targetDb = 'extensao_unb';
const db = db.getSiblingDB(targetDb);

print(`Seeding database: ${targetDb}`);

try {
  db.dropDatabase();
  print('Dropped existing database');
} catch (e) {
  print(e);
}

const usuarios = [
  {
    _id: 'student-1',
    nome: 'Gabriela Não Sei Das Quantas',
    email: 'gabriela.naoseidasquantas@aluno.unb.br',
    papeis: ['student'],
    unidade: 'Ciência da Computação',
    semestre: '6º semestre',
    interesses: ['Extensão', 'Dados', 'Educação'],
    bio: 'Estudante de Ciência da Computação interessada em projetos de impacto social, análise de dados e desenvolvimento de produtos digitais para a comunidade acadêmica.',
    criadoEm: new Date('2026-03-01T00:00:00Z'),
    ativo: true
  },
  {
    _id: 'prof-2',
    nome: 'Prof. Carlos Mendes',
    email: 'carlos.mendes@unb.br',
    papeis: ['professor'],
    unidade: 'Departamento de Ciência da Computação',
    interesses: ['Extensão', 'Inclusão', 'Dados', 'Tecnologia educacional'],
    bio: 'Docente com atuação em projetos de extensão e inclusão digital, com foco em formação tecnológica para a comunidade.',
    criadoEm: new Date('2026-03-02T00:00:00Z'),
    ativo: true
  },
  {
    _id: 'student-2',
    nome: 'Lucas Pereira',
    email: 'lucas.pereira@aluno.unb.br',
    papeis: ['student'],
    unidade: 'Ciência da Computação',
    semestre: '4º semestre',
    interesses: ['Extensão', 'Inclusão', 'Tecnologia educacional'],
    bio: 'Interesse em oficinas e ações de inclusão digital com foco em impacto social.',
    criadoEm: new Date('2026-03-03T00:00:00Z'),
    ativo: true
  },
  {
    _id: 'student-3',
    nome: 'Ana Clara Souza',
    email: 'ana.clara@aluno.unb.br',
    papeis: ['student'],
    unidade: 'Engenharia de Software',
    semestre: '7º semestre',
    interesses: ['Dados', 'Educação', 'Painéis', 'Análise de indicadores'],
    bio: 'Atua com análise de dados acadêmicos e construção de painéis para gestão educacional.',
    criadoEm: new Date('2026-03-04T00:00:00Z'),
    ativo: true
  },
  {
    _id: 'student-4',
    nome: 'Rafael Lima',
    email: 'rafael.lima@aluno.unb.br',
    papeis: ['student'],
    unidade: 'Sistemas de Informação',
    semestre: '3º semestre',
    interesses: ['Acolhimento', 'Mentoria', 'Extensão'],
    bio: 'Quer participar de projetos de acolhimento e mentoria para estudantes ingressantes.',
    criadoEm: new Date('2026-03-05T00:00:00Z'),
    ativo: true
  },
  {
    _id: 'student-5',
    nome: 'Beatriz Alves',
    email: 'beatriz.alves@aluno.unb.br',
    papeis: ['student'],
    unidade: 'Comunicação Organizacional',
    semestre: '5º semestre',
    interesses: ['Divulgação', 'Design', 'Ciência Aberta'],
    bio: 'Interesse em comunicação científica e design de materiais para ações universitárias.',
    criadoEm: new Date('2026-03-06T00:00:00Z'),
    ativo: true
  }
];

const projetos = [
  {
    _id: 'proj-1',
    titulo: 'Plataforma de monitoria com IA para disciplinas iniciais',
    area: 'Ciências Exatas e da Terra',
    unidadeResponsavel: 'Departamento de Ciência da Computação (CIC)',
    resumo: 'Projeto para apoiar estudantes em Cálculo e Programação com trilhas de estudo, revisão automática e acompanhamento de progresso.',
    coordenador: 'Prof. Ana Paula',
    cronograma: 'Inscrições até 18/03',
    tags: ['IA', 'Ensino', 'Web App'],
    status: 'aberto',
    quantidadeParticipantes: 12,
    proximoEvento: { titulo: 'Workshop de revisão de Cálculo', inicioEm: '2026-03-12T10:00:00Z' }
  },
  {
    _id: 'proj-2',
    titulo: 'Mapa de oportunidades acadêmicas e extensão universitária',
    area: 'Ciências Sociais Aplicadas',
    unidadeResponsavel: 'Faculdade de Administração, Contabilidade, Economia e Gestão Pública (FACE)',
    resumo: 'Centraliza bolsas, extensão, eventos e iniciativas estudantis para facilitar o acesso dos alunos a projetos ativos no campus.',
    coordenador: 'PET Computação',
    cronograma: 'Encontro quinta, 14h',
    tags: ['Extensão', 'Dados', 'Impacto social'],
    status: 'aberto',
    quantidadeParticipantes: 18,
    proximoEvento: { titulo: 'Plantão de orientação', inicioEm: '2026-03-14T15:30:00Z' }
  },
  {
    _id: 'proj-3',
    titulo: 'Laboratório de UX para serviços digitais da universidade',
    area: 'Linguística, Letras e Artes',
    unidadeResponsavel: 'Faculdade de Comunicação (FAC)',
    resumo: 'Grupo multidisciplinar focado em melhorar sistemas internos da universidade com pesquisa com usuários, prototipação e validação.',
    coordenador: 'Lab de Inovação',
    cronograma: 'Vagas abertas',
    tags: ['UX', 'Pesquisa', 'Protótipos'],
    status: 'aberto',
    quantidadeParticipantes: 9,
    proximoEvento: { titulo: 'Sessão de teste com usuários', inicioEm: '2026-03-18T09:00:00Z' }
  }
];

const eventos = [
  {
    _id: 'event-1',
    projetoId: 'proj-1',
    titulo: 'Workshop de iniciação científica',
    inicioEm: '2026-03-12T10:00:00Z',
    fimEm: '2026-03-12T12:00:00Z',
    local: 'Auditório da Faculdade',
    tipo: 'workshop'
  },
  {
    _id: 'event-2',
    projetoId: 'proj-2',
    titulo: 'Plantão de projetos de extensão',
    inicioEm: '2026-03-14T15:30:00Z',
    fimEm: '2026-03-14T17:00:00Z',
    local: 'Sala 204 - Bloco B',
    tipo: 'plantao'
  },
  {
    _id: 'event-3',
    projetoId: 'proj-3',
    titulo: 'Feira de grupos estudantis',
    inicioEm: '2026-03-18T09:00:00Z',
    fimEm: '2026-03-18T13:00:00Z',
    local: 'Praça Central do Campus',
    tipo: 'feira'
  }
];

const oportunidades = [
  {
    _id: 'opp-1',
    projetoId: 'proj-1',
    titulo: 'Bolsa de apoio em pesquisa e análise de dados educacionais',
    descricao: 'Atue no acompanhamento de indicadores acadêmicos, construção de relatórios e organização de painéis para apoiar ações de permanência estudantil.',
    sobreProjeto: 'Esta ação integra o Observatório de dados para permanência estudantil e atua no monitoramento de indicadores acadêmicos para apoiar decisões institucionais baseadas em evidências.',
    objetivos: [
      'Consolidar indicadores de permanência e desempenho estudantil',
      'Apoiar a produção de relatórios para acompanhamento de políticas acadêmicas',
      'Fortalecer a cultura de análise de dados aplicada à educação'
    ],
    atividadesDesenvolvidas: [
      'Coleta e organização de bases de dados acadêmicos',
      'Construção de painéis e visualizações para acompanhamento de métricas',
      'Apoio na redação de relatórios técnicos e sínteses executivas'
    ],
    comoParticipar: [
      'Clique em Saiba Mais e revise os requisitos da oportunidade',
      'Envie histórico acadêmico e breve carta de motivação para a coordenação',
      'Aguarde retorno por e-mail com orientações sobre a etapa de entrevista'
    ],
    certificado: true,
    requisitos: ['Conhecimento básico em planilhas', 'Organização', 'Interesse em análise de dados'],
    prazoInscricao: '2026-03-18',
    tipo: 'bolsa',
    local: 'Campus Darcy Ribeiro',
    cargaHoraria: '12h semanais',
    status: 'aberta',
    criadoEm: '2026-03-01T00:00:00Z',
    atualizadoEm: '2026-03-01T00:00:00Z',
    projeto: {
      _id: 'proj-1',
      titulo: 'Observatório de dados para permanência estudantil',
      area: 'Ciências Humanas',
      unidadeResponsavel: 'Faculdade de Educação (FE)',
      resumo: 'Projeto voltado ao uso de dados para melhorar políticas de acompanhamento estudantil.',
      tags: ['Dados', 'Educação'],
      status: 'aberto',
      vagas: 2
    }
  },
  {
    _id: 'opp-2',
    projetoId: 'proj-2',
    titulo: 'Voluntariado em ações de extensão para inclusão digital',
    descricao: 'Colabore em oficinas introdutórias de tecnologia para a comunidade e ajude na facilitação das atividades presenciais do projeto.',
    sobreProjeto: 'O projeto Conecta Comunidade promove inclusão digital em territórios do DF por meio de oficinas práticas, aproximando universidade e comunidade em ações de extensão.',
    objetivos: [
      'Ampliar o acesso da comunidade a competências digitais básicas',
      'Promover autonomia no uso de ferramentas tecnológicas do cotidiano',
      'Estimular a participação estudantil em ações de impacto social'
    ],
    atividadesDesenvolvidas: [
      'Apoio na preparação de materiais didáticos para oficinas',
      'Facilitação de turmas presenciais e suporte individual aos participantes',
      'Registro de presença e acompanhamento do desenvolvimento das turmas'
    ],
    comoParticipar: [
      'Realize a inscrição pelo botão Saiba Mais',
      'Anexe uma apresentação curta com sua disponibilidade semanal',
      'Participe do encontro de alinhamento com a equipe extensionista'
    ],
    certificado: true,
    requisitos: ['Boa comunicação', 'Interesse em impacto social', 'Disponibilidade aos sábados'],
    prazoInscricao: '2026-03-22',
    tipo: 'voluntariado',
    local: 'Ceilândia e atividades híbridas',
    cargaHoraria: '8h semanais',
    status: 'aberta',
    criadoEm: '2026-03-05T00:00:00Z',
    atualizadoEm: '2026-03-05T00:00:00Z',
    projeto: {
      _id: 'proj-2',
      titulo: 'Conecta Comunidade',
      area: 'Ciências Sociais Aplicadas',
      unidadeResponsavel: 'Departamento de Ciência da Computação (CIC)',
      resumo: 'Iniciativa que promove letramento digital em escolas e centros comunitários do DF.',
      tags: ['Extensão', 'Inclusão'],
      status: 'aberto',
      vagas: 6
    }
  },
  {
    _id: 'opp-3',
    projetoId: 'proj-3',
    titulo: 'Bolsa para design de materiais de divulgação científica',
    descricao: 'Produza peças digitais, organize conteúdos de redes sociais e apoie campanhas de divulgação das ações do laboratório.',
    sobreProjeto: 'O LabMídia Ciência Aberta busca tornar conteúdos científicos acessíveis para públicos diversos por meio de comunicação visual clara e linguagem inclusiva.',
    objetivos: [
      'Traduzir conteúdos científicos em materiais visuais acessíveis',
      'Aumentar o alcance das ações de divulgação científica do laboratório',
      'Consolidar identidade visual das campanhas institucionais'
    ],
    atividadesDesenvolvidas: [
      'Criação de peças para redes sociais, site e materiais institucionais',
      'Apoio na organização de calendário editorial e campanhas temáticas',
      'Adequação de linguagem e formato para diferentes públicos'
    ],
    comoParticipar: [
      'Envie portfólio com trabalhos autorais e sua disponibilidade semanal',
      'Preencha o formulário de inscrição indicado na página da oportunidade',
      'Aguarde contato da coordenação para avaliação técnica'
    ],
    certificado: false,
    requisitos: ['Noções de design', 'Criatividade', 'Conhecimento básico em ferramentas visuais'],
    prazoInscricao: '2026-03-25',
    tipo: 'bolsa',
    local: 'Instituto Central de Ciências',
    cargaHoraria: '10h semanais',
    status: 'aberta',
    criadoEm: '2026-03-08T00:00:00Z',
    atualizadoEm: '2026-03-08T00:00:00Z',
    projeto: {
      _id: 'proj-3',
      titulo: 'LabMídia Ciência Aberta',
      area: 'Linguística, Letras e Artes',
      unidadeResponsavel: 'Faculdade de Comunicação (FAC)',
      resumo: 'Projeto dedicado à divulgação científica acessível para diferentes públicos.',
      tags: ['Design', 'Divulgação'],
      status: 'aberto',
      vagas: 1
    }
  },
  {
    _id: 'opp-4',
    projetoId: 'proj-4',
    titulo: 'Voluntariado em mentoria para calouros',
    descricao: 'Apoie estudantes ingressantes com encontros de acolhimento, orientação sobre rotinas acadêmicas e suporte inicial nas primeiras semanas.',
    sobreProjeto: 'A Rede de acolhimento estudantil oferece suporte aos ingressantes com ações de mentoria e integração, reduzindo barreiras na adaptação à vida universitária.',
    objetivos: [
      'Facilitar a adaptação acadêmica e social de estudantes ingressantes',
      'Promover rede de apoio entre veteranos e calouros',
      'Fortalecer o vínculo dos estudantes com os serviços institucionais'
    ],
    atividadesDesenvolvidas: [
      'Condução de encontros de acolhimento e rodas de conversa',
      'Orientação sobre sistemas acadêmicos, prazos e rotinas da universidade',
      'Encaminhamento de demandas aos setores de apoio estudantil'
    ],
    comoParticipar: [
      'Inscreva-se na oportunidade e informe cursos/turnos de disponibilidade',
      'Participe da formação inicial para mentores voluntários',
      'Inicie atuação acompanhando grupos de ingressantes'
    ],
    certificado: true,
    requisitos: ['Empatia', 'Boa escuta', 'Disponibilidade para encontros presenciais'],
    prazoInscricao: '2026-03-28',
    tipo: 'voluntariado',
    local: 'Campus Gama',
    cargaHoraria: '6h semanais',
    status: 'aberta',
    criadoEm: '2026-03-10T00:00:00Z',
    atualizadoEm: '2026-03-10T00:00:00Z',
    projeto: {
      _id: 'proj-4',
      titulo: 'Rede de acolhimento estudantil',
      area: 'Ciências Humanas',
      unidadeResponsavel: 'Decanato de Assuntos Comunitários (DAC)',
      resumo: 'Programa de apoio e integração para novos estudantes da universidade.',
      tags: ['Acolhimento', 'Mentoria'],
      status: 'aberto',
      vagas: 10
    }
  }
];

db.createCollection('usuarios');
db.createCollection('projetos');
db.createCollection('eventos');
db.createCollection('oportunidades');

db.usuarios.insertMany(usuarios);
db.projetos.insertMany(projetos);
db.eventos.insertMany(eventos);
db.oportunidades.insertMany(oportunidades);

db.projetos.createIndex({ tags: 1 });
db.projetos.createIndex({ status: 1 });
db.eventos.createIndex({ projetoId: 1, inicioEm: 1 });
db.oportunidades.createIndex({ projetoId: 1, tipo: 1 });
db.oportunidades.createIndex({ projetoId: 1, status: 1 });

print('Indexes created.');
print('Counts:');
printjson({
  usuarios: db.usuarios.countDocuments(),
  projetos: db.projetos.countDocuments(),
  eventos: db.eventos.countDocuments(),
  oportunidades: db.oportunidades.countDocuments()
});

print('Done.');
